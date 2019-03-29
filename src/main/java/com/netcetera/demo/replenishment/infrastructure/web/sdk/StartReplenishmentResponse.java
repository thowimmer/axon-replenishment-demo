package com.netcetera.demo.replenishment.infrastructure.web.sdk;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
class StartReplenishmentResponse {

    @NotNull
    private final String replenishmentId;
}
