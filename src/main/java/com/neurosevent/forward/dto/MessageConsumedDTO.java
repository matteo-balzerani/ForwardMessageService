package com.neurosevent.forward.dto;

import java.io.Serializable;

public class MessageConsumedDTO implements Serializable {

	private static final long serialVersionUID = -7183137239239814734L;

	private String topic;
	private String payload;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public MessageConsumedDTO(String topic, String payload) {
		super();
		this.topic = topic;
		this.payload = payload;
	}

	public MessageConsumedDTO() {
	}

}
