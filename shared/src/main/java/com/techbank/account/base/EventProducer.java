package com.techbank.account.base;

import com.techbank.account.base.events.BaseEvent;

public interface EventProducer {
    void produce(String topicName, BaseEvent event);
}
