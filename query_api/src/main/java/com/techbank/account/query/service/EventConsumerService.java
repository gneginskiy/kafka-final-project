package com.techbank.account.query.service;

import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.query.event_handlers.AccountEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumerService {
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
    public void consume(@Payload AccountFundsDepositedEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = {"FundsWithdrawnEvent"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload AccountFundsWithdrawnEvent event, Acknowledgment ack) {
        accountEventHandler.handle(event);
        ack.acknowledge();
    }

}
