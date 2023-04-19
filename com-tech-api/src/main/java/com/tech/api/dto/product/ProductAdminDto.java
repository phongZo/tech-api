package com.tech.api.dto.product;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.productconfig.ProductConfigAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAdminDto extends ABasicAdminDto {

    @ApiModelProperty(name = "productCategoryId")
    private Long productCategoryId;

    @ApiModelProperty(name = "productCategoryName")
    private String productCategoryName;

    private Integer totalInStock;

    @ApiModelProperty(name = "description")
    private String description;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "price")
    private Double price;

    @ApiModelProperty(name = "image")
    private String image;

    @ApiModelProperty(name = "isSoldOut")
    private Boolean isSoldOut;

    @ApiModelProperty(name = "parentProductId")
    private Long parentProductId;

    private Boolean isSaleOff = false;
    private Integer saleOff;

    @ApiModelProperty(name = "productConfigs")
    private List<ProductConfigAdminDto> productConfigs;
}
