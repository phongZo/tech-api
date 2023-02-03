package com.tech.api.form.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdatePermissionForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotBlank(message = "name cant not be blank")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotBlank(message = "action cant not be blank")
    @ApiModelProperty(name = "action", required = true)
    private String action;

    @NotNull(message = "showMenu cant not be null")
    @ApiModelProperty(name = "showMenu", required = true)
    private Boolean showMenu;

    @NotBlank(message = "description cant not be blank")
    @ApiModelProperty(name = "description", required = true)
    private String description;

    @NotBlank(message = "nameGroup cant not be blank")
    @ApiModelProperty(name = "nameGroup", required = true)
    private String nameGroup;
}
