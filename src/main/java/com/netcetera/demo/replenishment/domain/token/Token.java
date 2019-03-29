package com.netcetera.demo.replenishment.domain.token;

import com.netcetera.demo.replenishment.api.command.CreateTokenCommand;
import com.netcetera.demo.replenishment.api.command.StartReplenishmentCommand;
import com.netcetera.demo.replenishment.api.dto.TokenStatus;
import com.netcetera.demo.replenishment.api.event.TokenCreatedEvent;
import com.netcetera.demo.replenishment.domain.replenishment.Replenishment;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static com.netcetera.demo.replenishment.api.dto.TokenStatus.ACTIVE;
import static com.netcetera.demo.replenishment.domain.util.ValidationUtil.assertState;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

@Aggregate
@NoArgsConstructor
public class Token {

    @AggregateIdentifier
    private String tokenId;

    private TokenStatus tokenStatus;

    @CommandHandler
    public Token(CreateTokenCommand command){
        String tokenId = UUID.randomUUID().toString();
        apply(new TokenCreatedEvent(tokenId, command.getTokenStatus()));
    }

    @EventSourcingHandler
    public void on(TokenCreatedEvent event){
        this.tokenId = event.getTokenId();
        this.tokenStatus = event.getTokenStatus();
    }

    @CommandHandler
    public String handle(StartReplenishmentCommand command) throws Exception {
        assertState(tokenStatus, ACTIVE);

        String replenishmentId = UUID.randomUUID().toString();
        createNew(Replenishment.class, () -> new Replenishment(replenishmentId, tokenId));

        return replenishmentId;
    }
}
