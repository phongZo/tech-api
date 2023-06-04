package com.tech.api.form.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePickShiftGhnForm {
    @JsonProperty("pick_shift")
    private List<Integer> pickingShift;

    @JsonProperty("order_code")
    private String orderCode;
}
