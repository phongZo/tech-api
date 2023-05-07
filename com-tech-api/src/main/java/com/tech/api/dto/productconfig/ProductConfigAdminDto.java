package com.tech.api.dto.productconfig;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.productvariant.ProductVariantAdminDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductConfigAdminDto extends ABasicAdminDto {
    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "variants")
    private List<ProductVariantDto> variants;
}
