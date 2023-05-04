package com.tech.api.mapper;

import com.tech.api.dto.customer.CustomerAddressDto;
import com.tech.api.form.customer.CreateAddressForm;
import com.tech.api.form.customer.UpdateAddressForm;
import com.tech.api.storage.model.CustomerAddress;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerMapper.class}
)
public interface CustomerAddressMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(source = "typeAddress", target = "typeAddress")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    @Mapping(source = "createdBy", target = "createdBy")
    @BeanMapping(ignoreByDefault = true)
    @Named("clientGetCustomerAddress")
    CustomerAddressDto fromEntityToDto(CustomerAddress address);

    @IterableMapping(elementTargetType = CustomerAddressDto.class, qualifiedByName = "clientGetCustomerAddress")
    List<CustomerAddressDto> fromEntityListToAddressDto(List<CustomerAddress> addressList);

    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(source = "typeAddress", target = "typeAddress")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    @BeanMapping(ignoreByDefault = true)
    @Named("createCustomerAddress")
    CustomerAddress fromCreateFormToEntity(CreateAddressForm address);

    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(source = "typeAddress", target = "typeAddress")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateCustomerAddress")
    void fromUpdateFormToEntity(UpdateAddressForm address, @MappingTarget CustomerAddress customerAddress);
}
