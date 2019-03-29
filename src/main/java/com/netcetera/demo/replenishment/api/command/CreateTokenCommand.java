package com.netcetera.demo.replenishment.api.command;

import com.netcetera.demo.replenishment.api.dto.TokenStatus;
import lombok.Value;

@Value
public final class CreateTokenCommand {

    private final TokenStatus tokenStatus;
}
