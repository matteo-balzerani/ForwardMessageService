package com.neurosevent.forward.rest;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	private final Logger log = LoggerFactory.getLogger(MessagesController.class);

//	@GetMapping("/consume")
//	public SseEmitter consume(@RequestBody List<String> topics, @RequestParam Map<String, String> consumerParams) {
//		log.debug("REST request to consume records from Kafka topics {}", topics);
//		Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
//		consumerProps.putAll(consumerParams);
//		consumerProps.remove("topic");
//	}

	@KafkaListener(topics = "test1", clientIdPrefix = "string", containerFactory = "kafkaListenerStringContainerFactory")
	public void listenasString(ConsumerRecord<String, String> cr, @Payload String payload) {
		log.info("READ [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(), payload,
				cr.toString());
	}

}
