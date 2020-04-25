package com.neurosevent.forward.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.neurosevent.forward.config.KafkaProperties;

@Service
public class MessageConsumer {

	private final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

	private final KafkaProperties kafkaProperties;
	private ExecutorService sseExecutorService = Executors.newCachedThreadPool();

	public MessageConsumer(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@PostConstruct
	public SseEmitter consume() {
		log.info("consume records from Kafka all topics");
		Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();

		SseEmitter emitter = new SseEmitter(0L);
		sseExecutorService.execute(() -> {
			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
			List<String> listTopics;
			try {
				listTopics = retrieveAllTopics(consumer);

				emitter.onCompletion(consumer::close);
				consumer.subscribe(listTopics);
				boolean exitLoop = false;
				while (!exitLoop) {
					try {
						List<String> listTopicsNew = retrieveAllTopics(consumer);
						if (!listTopicsNew.equals(listTopics)) {
							consumer.subscribe(listTopicsNew);
						}
						ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
						for (ConsumerRecord<String, String> record : records) {
							emitter.send(record.value());
							log.info(record.value());
						}
						emitter.send(SseEmitter.event().comment(""));
					} catch (Exception ex) {
						log.trace("Complete with error {}", ex.getMessage(), ex);
						emitter.completeWithError(ex);
						exitLoop = true;
					}
				}
				consumer.close();
				emitter.complete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return emitter;

	}

	private List<String> retrieveAllTopics(KafkaConsumer<String, String> consumer) throws Exception {
		Map<String, List<PartitionInfo>> topics = consumer.listTopics();
		List<String> listTopics = new ArrayList<String>(topics.keySet());
		listTopics.removeIf(x -> x.startsWith("__"));
		return listTopics;
	}
}

