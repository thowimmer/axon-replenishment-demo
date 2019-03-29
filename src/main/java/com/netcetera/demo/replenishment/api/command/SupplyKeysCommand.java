package com.netcetera.demo.replenishment.api.command;

import com.netcetera.demo.replenishment.api.dto.Key;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Value
public final class SupplyKeysCommand {

    @TargetAggregateIdentifier
    private final String replenishmentId;

    private final List<Key> keys;
}
