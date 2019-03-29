package com.netcetera.demo.replenishment.domain.replenishment;

import com.netcetera.demo.replenishment.api.command.ConfirmKeyDeliveryCommand;
import com.netcetera.demo.replenishment.api.command.FinishReplenishmentCommand;
import com.netcetera.demo.replenishment.api.command.SupplyKeysCommand;
import com.netcetera.demo.replenishment.api.event.KeyDeliveryConfirmedEvent;
import com.netcetera.demo.replenishment.api.event.KeysSuppliedEvent;
import com.netcetera.demo.replenishment.api.event.ReplenishmentFinishedEvent;
import com.netcetera.demo.replenishment.api.event.ReplenishmentStartedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static com.netcetera.demo.replenishment.domain.replenishment.ReplenishmentStatus.*;
import static com.netcetera.demo.replenishment.domain.util.ValidationUtil.assertState;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Replenishment {

    @AggregateIdentifier
    private String replenishmentId;

    private String tokenId;

    private ReplenishmentStatus status;

    public Replenishment(String replenishmentId, String tokenId){
        apply(new ReplenishmentStartedEvent(replenishmentId, tokenId));
    }

    @EventSourcingHandler
    public void on(ReplenishmentStartedEvent event){
        this.replenishmentId = event.getReplenishmentId();
        this.tokenId = event.getTokenId();
        this.status = STARTED;
    }

    @CommandHandler
    public void handle(SupplyKeysCommand command){
        assertState(status, STARTED);
        apply(new KeysSuppliedEvent(replenishmentId, tokenId, command.getKeys()));
    }

    @EventSourcingHandler
    public void on(KeysSuppliedEvent event){
        this.status = KEYS_SUPPLIED;
    }

    @CommandHandler
    public void handle(ConfirmKeyDeliveryCommand command){
        assertState(status, KEYS_SUPPLIED);
        apply(new KeyDeliveryConfirmedEvent(replenishmentId, tokenId));
    }

    @EventSourcingHandler
    public void on(KeyDeliveryConfirmedEvent event){
        this.status = KEY_DELIVERY_CONFIRMED;
    }

    @CommandHandler
    public void handle(FinishReplenishmentCommand command){
        assertState(status, KEY_DELIVERY_CONFIRMED);
        apply(new ReplenishmentFinishedEvent(replenishmentId, tokenId));
    }

    @EventSourcingHandler
    public void on(ReplenishmentFinishedEvent event){
        this.status = FINISHED;
    }
}
