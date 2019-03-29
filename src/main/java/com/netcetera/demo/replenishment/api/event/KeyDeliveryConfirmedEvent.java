package com.netcetera.demo.replenishment.api.event;

import lombok.Value;

@Value
public final class KeyDeliveryConfirmedEvent {

    private final String replenishmentId;

    private final String tokenId;
}
