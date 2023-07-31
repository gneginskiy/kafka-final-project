package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.ReplayAccountEventsCommand;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AccountReplayService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateRepository accountAggregateRepository;
    private final AccountEventSender eventSender;
    private final AccountCommandToEventMapper eventMapper;
    private static final AtomicBoolean isReplay = new AtomicBoolean(false);


    public void runReplay(ReplayAccountEventsCommand cmd) {
        var replayStartedEvent   = eventMapper.buildEvent(cmd);
        var replayCompletedEvent = eventMapper.buildReplayCompletedEvent();
        isReplay.set(true);
        isReplay.set(false);
    }
//
//    public void apply(BaseEvent evt) {
//        if      (evt instanceof AccountsReplayStartedEvent   e) apply(e);
//        else if (evt instanceof AccountsReplayCompletedEvent e) apply(e);
//        else if (evt instanceof AccountOpenedEvent           e) apply(e);
//        else if (evt instanceof AccountFundsDepositedEvent   e) apply(e);
//        else if (evt instanceof AccountFundsWithdrawnEvent   e) apply(e);
//        else if (evt instanceof AccountClosedEvent           e) apply(e);
//        else throw ApiError.internalServerError("No handler for evt " + evt.getClass(), null);
//        eventSender.send(evt);
//    }
//
//
//    public void apply(AccountsReplayCompletedEvent evt) {
//        //marker event
//    }
//
//    public void apply(AccountsReplayStartedEvent markerEvent) {
//        accountAggregateRepository.deleteAll();
//        int counter = 0;
//        Pageable pageable = PageRequest.of(0, 1000);
//        do {
//            var page = eventsRepository.findAll(pageable);
//            for (var evt : page) {
//                long start = System.currentTimeMillis();
//                this.appl.handle(rmmEventEntity);
//                log.error(rmmEventEntity.getRmmEventType() + " " + (counter++) + " | took " + (System.currentTimeMillis() - start) + " ms");
//            }
//            pageable = page.nextPageable();
//        } while (pageable.isPaged());
//
//        eventsRepository.findAll(new PageRequest(0).withSort().withPage())
//
//
//    }
//
//    private void apply(AccountOpenedEvent event) {
//        AccountAggregate aggregate = accountAggregateRepository.save(buildNewAggregate(event));
//    }
//
//    private void apply(AccountFundsDepositedEvent event) {
//        var aggregate = fetchAggregate(event);
//        aggregate.setBalance(aggregate.getBalance().add(event.getAmount()));
//        accountAggregateRepository.save(aggregate);
//    }
//
//    private void apply(AccountFundsWithdrawnEvent event) {
//        var aggregate = fetchAggregate(event);
//        aggregate.setBalance(aggregate.getBalance().subtract(event.getAmount()));
//        accountAggregateRepository.save(aggregate);
//    }
//
//    private void apply(AccountClosedEvent event) {
//        var aggregate = fetchAggregate(event);
//        aggregate.setActive(false);
//        accountAggregateRepository.save(aggregate);
//    }
//
//    private AccountAggregate fetchAggregate(BaseEvent event) {
//        return getById(event.getAggregateId());
//    }
//
//    public AccountAggregate getById(UUID id) {
//        return accountAggregateRepository.findById(id).orElse(null);
//    }
//
//    private static AccountAggregate buildNewAggregate(AccountOpenedEvent event) {
//        return new AccountAggregate()
//                .setId(event.getAggregateId())
//                .setActive(true)
//                .setBalance(event.getOpeningBalance())
//                .setVersion(0);
//    }
//
//    private static EventEntity toEventEntity(BaseEvent event, AccountAggregate aggregate) {
//        return new EventEntity()
//                .setAggregateId(aggregate.getId())
//                .setAggregateType(aggregate.getClass().getSimpleName())
//                .setEventType(event.getClass().getSimpleName())
//                .setEventData(event)
//                .setTimestamp(readTimestamp(event));
//    }

}
