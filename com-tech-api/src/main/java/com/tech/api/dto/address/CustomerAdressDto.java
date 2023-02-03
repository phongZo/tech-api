package com.tech.api.dto.address;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.customer.CustomerDto;
import lombok.Data;

@Data
public class CustomerAdressDto extends ABasicAdminDto {
    private CustomerDto customerDto;
    private String addressDetails;
    private String receiverFullName;
    private String phone;
    private Boolean isDefault;
    private String note;
}
