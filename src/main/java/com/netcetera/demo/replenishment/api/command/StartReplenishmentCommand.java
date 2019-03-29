package com.netcetera.demo.replenishment.api.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class StartReplenishmentCommand {

    @TargetAggregateIdentifier
    private final String tokenId;
}
