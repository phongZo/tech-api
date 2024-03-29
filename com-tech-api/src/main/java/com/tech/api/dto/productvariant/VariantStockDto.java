package com.tech.api.dto.productvariant;

import com.tech.api.dto.store.StoreDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariantStockDto {
    @ApiModelProperty(name = "addressDetails")
    private String addressDetails;
    private String name;
    private String phone;
    private Long storeId;

    private Integer totalInStock;

    public VariantStockDto(String addressDetails, String name, String phone, Integer totalInStock, Long storeId) {
        this.addressDetails = addressDetails;
        this.name = name;
        this.phone = phone;
        this.totalInStock = totalInStock;
        this.storeId = storeId;
    }
}
