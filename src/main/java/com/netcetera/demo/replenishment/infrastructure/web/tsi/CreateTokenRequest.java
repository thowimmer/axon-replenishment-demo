package com.netcetera.demo.replenishment.infrastructure.web.tsi;

import com.netcetera.demo.replenishment.api.dto.TokenStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
class CreateTokenRequest {

    @NotNull
    private TokenStatus tokenStatus;
}
