package com.tech.api.form.importForm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AcceptImportForm {
    @NotNull(message = "importId can not be null")
    @ApiModelProperty(required = true)
    private Long importId;
}
