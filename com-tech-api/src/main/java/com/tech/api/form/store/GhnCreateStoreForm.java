package com.tech.api.form.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GhnCreateStoreForm {
    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("ward_code")
    private String wardCode;

    private String name;
    private String phone;
    private String address;
}
