package com.techbank.account.query.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import com.techbank.account.dto.events.FundsDepositedEvent;
import com.techbank.account.dto.events.FundsWithdrawnEvent;
import com.techbank.account.query.event_handlers.AccountEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumerService {
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;
    private final AccountEventHandler accountEventHandler;

    @KafkaListener(topics = {"AccountOpenedEvent"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload AccountOpenedEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = {"AccountClosedEvent"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload AccountClosedEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = {"FundsDepositedEvent"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload FundsDepositedEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = {"FundsWithdrawnEvent"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

}
