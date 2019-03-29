package com.netcetera.demo.replenishment.infrastructure.web.sdk;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
class StartReplenishmentRequest {

    @NotNull
    private String tokenId;
}
