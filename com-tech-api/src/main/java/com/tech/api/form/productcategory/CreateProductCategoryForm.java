package com.tech.api.form.productcategory;

import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateProductCategoryForm {

    @ApiModelProperty(name = "parentId")
    private Long parentId;

    @NotBlank(message = "name can not be blank")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "Order sort can not be null")
    @ApiModelProperty(name = "orderSort", required = true)
    private Integer orderSort;

    @ApiModelProperty(name = "icon")
    private String icon;

    @ApiModelProperty(name = "note")
    private String note;

    @Status
    private Integer status = 1;
}
