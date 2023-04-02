package com.tech.api.form.productconfig;

import com.tech.api.validation.Status;
import com.tech.api.validation.VariantChoiceKind;
import com.tech.api.form.productvariant.CreateProductVariantForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateProductConfigForm {
    @NotBlank
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @Status
    @ApiModelProperty(name = "status")
    private Integer status = 1;

    @NotEmpty(message = "Product variant can not be empty")
    @ApiModelProperty(name = "variants", required = true)
    List<@Valid CreateProductVariantForm> variants = new ArrayList<>();
}
