package com.tech.api.dto.customer;

import com.tech.api.dto.account.AccountAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAdminDto {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("account")
    private AccountAdminDto account;

    @ApiModelProperty("gender")
    private Integer gender;

    @ApiModelProperty("birthday")
    private LocalDate birthday;

    @ApiModelProperty("note")
    private String note;

    @ApiModelProperty("addresses")
    private List<CustomerAddressDto> addresses;
}
