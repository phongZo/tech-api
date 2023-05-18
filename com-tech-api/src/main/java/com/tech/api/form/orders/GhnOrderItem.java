package com.tech.api.form.orders;

import lombok.Data;

@Data
public class GhnOrderItem {
    private String name;
    private Integer quantity;
    private Integer price;
}
