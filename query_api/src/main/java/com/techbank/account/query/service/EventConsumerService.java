package com.techbank.account.query.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.query.event_handlers.AccountEventHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumerService {
    private final AccountEventHandlerService accountEventHandlerService;

    @KafkaListener(topics = {"account-events-v1"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload BaseEvent baseEvent, Acknowledgment ack) {
        accountEventHandlerService.handle(baseEvent);
        ack.acknowledge();
    }

}
