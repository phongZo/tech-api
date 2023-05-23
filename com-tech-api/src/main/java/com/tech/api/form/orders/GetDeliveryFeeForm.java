package com.tech.api.form.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetDeliveryFeeForm {
    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("service_type_id")
    private Integer serviceType;

    @JsonProperty("from_district_id")
    private Integer fromDistrictId;

    @JsonProperty("to_district_id")
    private Long toDistrictId;

    @JsonProperty("to_ward_code")
    private String toWardCode;

    @JsonProperty("from_ward_code")
    private String fromWardCode;

    private Integer height = 10;
    private Integer length = 1;
    private Integer weight = 2000;
    private Integer width = 20;

    @JsonProperty("insurance_value")
    private Integer insuranceValue = 5000000;
}
