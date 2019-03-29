package com.netcetera.demo.replenishment.infrastructure.web.error;

import lombok.Value;

@Value
public final class ErrorResponse {

    private final String message;
}
