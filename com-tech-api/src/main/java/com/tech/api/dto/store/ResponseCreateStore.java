package com.tech.api.dto.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseCreateStore {
    @JsonProperty("shop_id")
    private Long shopId;
}
