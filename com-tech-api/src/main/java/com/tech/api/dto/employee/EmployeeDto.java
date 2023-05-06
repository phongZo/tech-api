package com.tech.api.dto.employee;

import com.tech.api.dto.account.AccountDto;
import com.tech.api.dto.category.CategoryDto;
import com.tech.api.dto.store.StoreDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("account")
    private AccountDto account;

    @ApiModelProperty("storeDto")
    private StoreDto storeDto;

    @ApiModelProperty("note")
    private String note;
}
