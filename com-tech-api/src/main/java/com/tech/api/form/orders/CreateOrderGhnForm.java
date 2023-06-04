package com.tech.api.form.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderGhnForm {
    @JsonProperty("payment_type_id")
    private Integer paymentTypeId;

    @JsonProperty("required_note")
    private String requireNote;

    @JsonProperty("to_name")
    private String toName;

    @JsonProperty("to_phone")
    private String toPhone;

    @JsonProperty("to_address")
    private String toAddress;

    @JsonProperty("to_ward_name")
    private String toWardName;

    @JsonProperty("to_district_name")
    private String toDistrictName;

    @JsonProperty("to_province_name")
    private String toProvinceName;

    @JsonProperty("cod_amount")
    private Integer codAmount;

    @JsonProperty("pick_shift")
    private List<Integer> pickingShift;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("cod_failed_amount")
    private Integer codeFailedAmount;

    @JsonProperty("insurance_value")
    private Integer insuranceValue;

    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("service_type_id")
    private Integer serviceTypeId;

    private List<GhnOrderItem> items;
}
