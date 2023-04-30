package com.tech.api.dto.productconfig;

import com.tech.api.dto.productvariant.ProductVariantDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductConfigDto {

    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "variants")
    private List<ProductVariantDto> variants;

    private Integer status;
}
