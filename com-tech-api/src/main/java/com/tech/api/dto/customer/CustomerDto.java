package com.tech.api.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.api.dto.account.AccountDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @ApiModelProperty("id")
    private Long id;

    private Double walletMoney;

    @ApiModelProperty("account")
    private AccountDto account;

    @ApiModelProperty("gender")
    private Integer gender;

    @ApiModelProperty("birthday")
    private LocalDate birthday;

    @ApiModelProperty("note")
    private String note;

    @ApiModelProperty("addresses")
    private List<CustomerAddressDto> addresses;

    private Integer loyaltyLevel;
    private Double loyaltyPoint;
}
