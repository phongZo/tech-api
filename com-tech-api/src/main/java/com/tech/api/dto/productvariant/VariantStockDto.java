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

    private Integer totalInStock;
}
