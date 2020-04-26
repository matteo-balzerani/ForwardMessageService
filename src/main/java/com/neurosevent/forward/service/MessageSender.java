package com.neurosevent.forward.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neurosevent.forward.dto.MessageConsumedDTO;

@Service
public class MessageSender {

	private final Logger log = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	private HttpHeaders httpHeaders;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objMapper;

	public Boolean sendToSubscriber(MessageConsumedDTO data) {
		try {
			String jsonStr = objMapper.writeValueAsString(data);
			HttpEntity<String> request = new HttpEntity<String>(jsonStr, httpHeaders);
			ResponseEntity<String> resultPost = restTemplate.postForEntity("http://localhost:1666/mock", request,
					String.class);
			log.info(resultPost.toString());
		} catch (Exception e) {
			log.error("error:  " + e.getMessage());
			return false;
		}
		return true;
	}

}
