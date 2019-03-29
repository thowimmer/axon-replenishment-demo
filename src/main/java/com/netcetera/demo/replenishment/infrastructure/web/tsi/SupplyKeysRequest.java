package com.netcetera.demo.replenishment.infrastructure.web.tsi;

import com.netcetera.demo.replenishment.api.dto.Key;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
class SupplyKeysRequest {

    @NotNull
    @NotEmpty
    private List<Key> keys;
}
