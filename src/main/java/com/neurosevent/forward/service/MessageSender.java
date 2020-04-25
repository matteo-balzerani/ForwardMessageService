package com.neurosevent.forward.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageSender {

	private final Logger log = LoggerFactory.getLogger(MessageSender.class);

	public Boolean sendToSubscriber(String data) {
//		TcpClient tcpClient = TcpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//				.doOnConnected(connection -> {
//					connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
//					connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
//				});
//		WebClient client = WebClient.builder()
//				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient))).build();
//
//		Mono<String> result = client.post().uri("localhost:8080").body(BodyInserters.fromFormData("payload", data))
//				.exchange().flatMap(clientResponse -> {
//					if (clientResponse.statusCode().is5xxServerError()) {
//						clientResponse.body((clientHttpResponse, context) -> {
//							log.info("ZERO");
//							return clientHttpResponse.getBody();
//						});
//						log.info("UNO");
//						return clientResponse.bodyToMono(String.class);
//					} else {
//						log.info("DUE");
//						return clientResponse.bodyToMono(String.class);
//
//					}
//				});
//
//		return true;
		log.debug("Starting REST Client!!!!");

		try {
			RestTemplate restTemplate = new RestTemplate();
//			String result = restTemplate.getForObject( String.class);
			ResponseEntity<String> resultPost= restTemplate.postForEntity("http://localhost:8089/mock", data, String.class);
			log.info(resultPost.toString());
		} catch (Exception e) {
			log.error("error:  " + e.getMessage());
		}
		return true;
	}

}
