package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.ReplayAccountEventsCommand;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.admin.AccountsReplayCompletedEvent;
import com.techbank.account.dto.events.admin.AccountsReplayStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountReplayService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateService aggregateService;
    private final AccountEventSender eventSender;

    public void runReplay(ReplayAccountEventsCommand cmd) {
        aggregateService.replayStart();
        replay();
        aggregateService.replayEnd();
    }

    public void replay() {
        eventSender.send(new AccountsReplayStartedEvent());
        int counter = 0;
        Pageable pageable = PageRequest.of(0, 1000);
        do {
            var page = eventsRepository.findAll(pageable);
            for (var evt : page) {
                long start = System.currentTimeMillis();
                aggregateService.apply(evt.getEventData());
                var msg = evt.getEventType() + " " + (counter++) + " | took " + (System.currentTimeMillis() - start) + " ms";
                log.info(msg);
            }
            pageable = page.nextPageable();
        } while (pageable.isPaged());
        eventSender.send(new AccountsReplayCompletedEvent());
    }



}
