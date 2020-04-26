package com.neurosevent.forward.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Field("topic")
	private String topic;

	@NotNull
	@Field("payload")
	private String payload;

	@CreatedDate
	@Field("created_date")
	@JsonIgnore
	private Instant createdDate = Instant.now();

	@Field("last_sent_date")
	@JsonIgnore
	private Instant lastSentDate = Instant.now();

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

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastSentDate() {
		return lastSentDate;
	}

	public void setLastSentDate(Instant lastSentDate) {
		this.lastSentDate = lastSentDate;
	}

}
