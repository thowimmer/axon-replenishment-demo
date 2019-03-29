package com.netcetera.demo.replenishment.infrastructure.persistence.reporting;

import com.netcetera.demo.replenishment.api.event.ReplenishmentFinishedEvent;
import com.netcetera.demo.replenishment.api.event.TokenCreatedEvent;
import com.netcetera.demo.replenishment.api.query.ReplenishmentPerTokenResponse;
import com.netcetera.demo.replenishment.api.query.ReplenishmentsPerTokenQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenReplenishmentsProjection {

    private final Map<String, Integer> replenishmentsPerTokenStore = new ConcurrentHashMap<>();

    @EventHandler
    public void on(TokenCreatedEvent event) {
        replenishmentsPerTokenStore.put(event.getTokenId(), 0);
    }

    @EventHandler
    public void on(ReplenishmentFinishedEvent event) {
        Integer amountOfReplenishments = replenishmentsPerTokenStore.get(event.getTokenId());
        replenishmentsPerTokenStore.put(event.getTokenId(), ++amountOfReplenishments);
    }

    @QueryHandler
    public ReplenishmentPerTokenResponse handle(ReplenishmentsPerTokenQuery query){
        String tokenId = query.getTokenId();
        Integer replenishmentAmount = replenishmentsPerTokenStore.get(tokenId);
        if(replenishmentAmount == null){
            throw new NoSuchElementException(String.format("Token with id '%s' not found.", tokenId));
        }
        return new ReplenishmentPerTokenResponse(replenishmentAmount);
    }
}
