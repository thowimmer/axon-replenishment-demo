package com.netcetera.demo.replenishment.api.event;

import com.netcetera.demo.replenishment.api.dto.TokenStatus;
import lombok.Value;

@Value
public final class TokenCreatedEvent {

    private final String tokenId;

    private final TokenStatus tokenStatus;
}
