package com.tech.api.dto.productcategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDto {
    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "parentId")
    private Long parentId;

    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "orderSort")
    private Integer orderSort;

    @ApiModelProperty(name = "icon")
    private String icon;

    @ApiModelProperty(name = "note")
    private String note;

    @ApiModelProperty(name = "status")
    private Integer status;
}
