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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neurosevent.forward.config.KafkaProperties;
import com.neurosevent.forward.dto.MessageConsumedDTO;

@Service
public class MessageConsumer {

	private final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

	private final int TIMEOUT = 5;
	private final int MAX_TRY = 5;


	private final KafkaProperties kafkaProperties;
	private ExecutorService sseExecutorService = Executors.newCachedThreadPool();

	public MessageConsumer(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private MessageStorer messageStorer;

	@PostConstruct
	public boolean consume() {
		log.info("consume records from Kafka all topics");
		Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
		sseExecutorService.execute(() -> {
			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
			List<String> listTopics;
			try {
				listTopics = retrieveAllTopics(consumer);
				consumer.subscribe(listTopics);
				int numOfTry = 0;
				boolean exitLoop = false;
				while (!exitLoop) {
					try {
						List<String> listTopicsNew = retrieveAllTopics(consumer);
						if (!listTopicsNew.equals(listTopics)) {
							consumer.subscribe(listTopicsNew);
						}
						ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(TIMEOUT));
						for (ConsumerRecord<String, String> record : records) {
							MessageConsumedDTO mess = new MessageConsumedDTO(record.topic(), record.value());
							if (!messageSender.sendToSubscriber(mess)) {
								messageStorer.save(mess);
							}
						}
						numOfTry = 0;
					} catch (Exception ex) {
						log.error("error {}", ex.getMessage(), ex);
						numOfTry++;
						if (numOfTry > MAX_TRY)
							exitLoop = true;
					}
				}
				consumer.close();
			} catch (Exception e) {
				log.error("error {}", e.getMessage(), e);
			}
		});
		return true;

	}

	private List<String> retrieveAllTopics(KafkaConsumer<String, String> consumer) throws Exception {
		Map<String, List<PartitionInfo>> topics = consumer.listTopics();
		List<String> listTopics = new ArrayList<String>(topics.keySet());
		listTopics.removeIf(x -> x.startsWith("__"));
		return listTopics;
	}
}
