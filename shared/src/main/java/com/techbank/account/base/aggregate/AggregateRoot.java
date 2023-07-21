package com.techbank.account.base.aggregate;

import com.techbank.account.base.events.BaseEventDto;
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
    private final List<BaseEventDto> changes = new ArrayList<>();

    public List<BaseEventDto> getUncommitedChanges() {
        return changes.stream().filter(this::isUncommited).toList();
    }

    private boolean isUncommited(BaseEventDto baseEventDto) {
        return true; //todo
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    protected void applyChange(BaseEventDto e, boolean newEvent) {
        try {
            this.apply(e);
        } finally {
            if (newEvent) changes.add(e);
        }
    }

    public abstract void apply(BaseEventDto event);

    public void raiseEvent(BaseEventDto event){
        applyChange(event,true);
    }

    public void replayEvents(Collection<BaseEventDto> events){
        events.forEach(event -> applyChange(event,false));
    }
}
