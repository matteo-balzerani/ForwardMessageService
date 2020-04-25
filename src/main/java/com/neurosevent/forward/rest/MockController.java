package com.neurosevent.forward.rest;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

	private final Logger log = LoggerFactory.getLogger(MockController.class);

	@PostMapping("/mock")
	public String publish(@RequestBody String data) throws ExecutionException, InterruptedException {
		log.info("received : {}", data);
		return "OK";
	}
}
