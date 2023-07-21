package com.techbank.account.cmd.service;

import com.techbank.account.base.EventProducer;
import com.techbank.account.base.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Override
    public void produce(String topicName, BaseEvent event) {
        kafkaTemplate.send(topicName, event);
    }
}
