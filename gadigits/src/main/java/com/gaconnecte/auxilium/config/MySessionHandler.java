package com.gaconnecte.auxilium.config;

import org.slf4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;
import org.slf4j.LoggerFactory;

import com.gaconnecte.auxilium.domain.Greeting;


public class MySessionHandler extends StompSessionHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/greetings", this);
        //session.send("/app/hello", "{\"nom\":\"Client\"}".getBytes());

        logger.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    	logger.info("Received: {}", ((Greeting) payload).getContent());
    }
}