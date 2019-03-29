package com.netcetera.demo.replenishment.api.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public final class FinishReplenishmentCommand {

    @TargetAggregateIdentifier
    private final String replenishmentId;
}
