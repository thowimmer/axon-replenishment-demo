package com.netcetera.demo.replenishment.api.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public final class ConfirmKeyDeliveryCommand {

    @TargetAggregateIdentifier
    private final String replenishmentId;
}
