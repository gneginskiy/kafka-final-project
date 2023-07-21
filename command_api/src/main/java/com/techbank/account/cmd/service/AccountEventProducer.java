package com.techbank.account.cmd.service;

import com.techbank.account.base.EventProducer;
import com.techbank.account.base.events.BaseEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {
    private final KafkaTemplate<String, BaseEventDto> kafkaTemplate;

    @Override
    public void produce(String topicName, BaseEventDto event) {
        kafkaTemplate.send(topicName, event);
    }
}
