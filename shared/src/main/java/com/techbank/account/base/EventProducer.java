package com.techbank.account.base;

import com.techbank.account.base.events.BaseEventDto;

public interface EventProducer {
    void produce(String topicName, BaseEventDto event);
}
