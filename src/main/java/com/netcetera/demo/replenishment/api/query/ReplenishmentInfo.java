package com.netcetera.demo.replenishment.api.query;

import com.netcetera.demo.replenishment.api.dto.Key;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class ReplenishmentInfo {

    public enum Status {

        STARTED,

        KEYS_READY,

        FINISHED
    }

    private Status status;

    private List<Key> replenishedKeys;
}
