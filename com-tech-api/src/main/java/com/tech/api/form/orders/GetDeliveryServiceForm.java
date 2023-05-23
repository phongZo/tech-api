package com.tech.api.form.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetDeliveryServiceForm {
    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("from_district")
    private Integer fromDistrictId;

    @JsonProperty("to_district")
    private Long toDistrictId;
}
