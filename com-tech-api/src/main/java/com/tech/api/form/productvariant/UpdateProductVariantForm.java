package com.tech.api.form.productvariant;

import com.tech.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateProductVariantForm {

    @ApiModelProperty(name = "id")
    private Long id;

    @NotBlank(message = "Product variant name can not be blank")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "Price can not be null")
    @ApiModelProperty(name = "price", required = true)
    private Double price;

    @ApiModelProperty(name = "image")
    private String image;

    @ApiModelProperty(name = "description")
    private String description;

    @Status
    @ApiModelProperty(name = "status")
    private Integer status = 1;
}
