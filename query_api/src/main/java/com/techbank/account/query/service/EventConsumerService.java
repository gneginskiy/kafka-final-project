package com.techbank.account.query.service;

import com.techbank.account.base.events.BaseEvent;
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

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventConsumerService {
    private final AccountEventHandler accountEventHandler;

    @KafkaListener(topics = {"account-events-v1"},groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload BaseEvent baseEvent, Acknowledgment ack) {
        accountEventHandler.handle(baseEvent);
        ack.acknowledge();
    }

}
