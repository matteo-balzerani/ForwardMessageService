package com.neurosevent.forward.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neurosevent.forward.domain.Subscriber;

@Repository
public interface SubscriberRepo extends MongoRepository<Subscriber, String> {
	
	List<Subscriber> findByTopic(String topic);
	

}
