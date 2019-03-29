package com.netcetera.demo.replenishment.api.event;

import com.netcetera.demo.replenishment.api.dto.Key;
import lombok.Value;

import java.util.List;

@Value
public final class KeysSuppliedEvent {

    private final String replenishmentId;

    private final String tokenId;

    private final List<Key> keys;
}
