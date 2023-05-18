package com.tech.api.form.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GhnCancelOrdersForm {
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
