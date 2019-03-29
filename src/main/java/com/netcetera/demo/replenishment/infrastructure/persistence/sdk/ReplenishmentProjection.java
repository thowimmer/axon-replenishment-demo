package com.netcetera.demo.replenishment.infrastructure.persistence.sdk;

import com.netcetera.demo.replenishment.api.dto.Key;
import com.netcetera.demo.replenishment.api.event.KeysSuppliedEvent;
import com.netcetera.demo.replenishment.api.event.ReplenishmentFinishedEvent;
import com.netcetera.demo.replenishment.api.event.ReplenishmentStartedEvent;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfo;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfoQuery;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfoResponse;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import static com.netcetera.demo.replenishment.api.query.ReplenishmentInfo.Status.*;

@Component
public class ReplenishmentProjection {

    private final Map<String, ReplenishmentInfo> replenishmentInfoStore = new ConcurrentHashMap<>();

    @EventHandler
    public void on(ReplenishmentStartedEvent event){
        ReplenishmentInfo replenishmentInfo = new ReplenishmentInfo(STARTED, new ArrayList<>());
        replenishmentInfoStore.put(event.getReplenishmentId(), replenishmentInfo);
    }

    @EventHandler
    public void on(KeysSuppliedEvent event){
        ReplenishmentInfo replenishmentInfo = replenishmentInfoStore.get(event.getReplenishmentId());

        List<Key> storedKeys = replenishmentInfo.getReplenishedKeys();
        storedKeys.addAll(event.getKeys());

        replenishmentInfo.setStatus(KEYS_READY);
    }

    @EventHandler
    public void on(ReplenishmentFinishedEvent event){
        ReplenishmentInfo replenishmentInfo = replenishmentInfoStore.get(event.getReplenishmentId());
        replenishmentInfo.setStatus(FINISHED);
    }

    @QueryHandler
    public ReplenishmentInfoResponse handle(ReplenishmentInfoQuery query){
        String replenishmentId = query.getReplenishmentId();
        ReplenishmentInfo replenishmentInfo = replenishmentInfoStore.get(replenishmentId);

        if(replenishmentInfo == null){
            throw new NoSuchElementException(String.format("Replenishment with id '%s' not found.", replenishmentId));
        }
        return new ReplenishmentInfoResponse(replenishmentInfo);
    }
}
