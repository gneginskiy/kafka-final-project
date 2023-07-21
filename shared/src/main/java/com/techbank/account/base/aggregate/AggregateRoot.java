package com.techbank.account.base.aggregate;

import com.techbank.account.base.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public abstract class AggregateRoot {
    @Getter @Setter
    protected String aggregateId;
    @Getter
    @Setter
    protected int version;
    private final List<BaseEvent> changes = new ArrayList<>();

    public List<BaseEvent> getUncommitedChanges() {
        return changes.stream().filter(this::isUncommited).toList();
    }

    private boolean isUncommited(BaseEvent baseEvent) {
        return true; //todo
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    protected void applyChange(BaseEvent e, boolean newEvent) {
        try {
            this.apply(e);
        } finally {
            if (newEvent) changes.add(e);
        }
    }

    public abstract void apply(BaseEvent event);

    public void raiseEvent(BaseEvent event){
        applyChange(event,true);
    }

    public void replayEvents(Collection<BaseEvent> events){
        events.forEach(event -> applyChange(event,false));
    }
}
