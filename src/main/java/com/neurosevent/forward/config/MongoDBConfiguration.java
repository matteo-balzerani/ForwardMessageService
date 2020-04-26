package com.neurosevent.forward.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.neurosevent.forward.repository")
public class MongoDBConfiguration {

}
