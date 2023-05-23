package com.tech.api.dto.ghn;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeliveryServiceResponse implements Serializable {
    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("short_name")
    private String name;

    @JsonProperty("service_type_id")
    private Integer type;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("leadtime")
    private Long leadTime;
}
