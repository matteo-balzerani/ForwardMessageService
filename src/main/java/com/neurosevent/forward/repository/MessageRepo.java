package com.neurosevent.forward.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neurosevent.forward.domain.Message;

@Repository
public interface MessageRepo extends MongoRepository<Message, String> {

}
