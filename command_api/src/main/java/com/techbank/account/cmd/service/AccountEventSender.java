package com.techbank.account.cmd.service;

import com.techbank.account.base.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventSender {
    private static final String TOPIC_NAME = "account-events-v1";

    private final AccountEventProducer accountEventProducer;

    public void send(BaseEvent event) {
        accountEventProducer.produce(TOPIC_NAME, event);
    }
}
