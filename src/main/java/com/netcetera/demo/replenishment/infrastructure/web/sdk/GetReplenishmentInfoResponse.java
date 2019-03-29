package com.netcetera.demo.replenishment.infrastructure.web.sdk;

import com.netcetera.demo.replenishment.api.dto.Key;
import com.netcetera.demo.replenishment.api.query.ReplenishmentInfo;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
final class GetReplenishmentInfoResponse {

    @NotNull
    private final ReplenishmentInfo.Status status;

    private final List<Key> replenishedKeys;
}
