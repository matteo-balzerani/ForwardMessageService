package com.neurosevent.forward.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neurosevent.forward.domain.Message;
import com.neurosevent.forward.dto.MessageConsumedDTO;
import com.neurosevent.forward.repository.MessageRepo;

@Service
public class MessageStorer {

	private final Logger log = LoggerFactory.getLogger(MessageStorer.class);

	@Autowired
	private MessageRepo messageRepo;

	public Message save(MessageConsumedDTO mess, String url) {
		log.info("saving {}", mess.toString());
		Message message = new Message();
		message.setPayload(mess.getPayload());
		message.setTopic(mess.getTopic());
		message.setSubscriberUrl(url);
		return messageRepo.save(message);
	}

}
