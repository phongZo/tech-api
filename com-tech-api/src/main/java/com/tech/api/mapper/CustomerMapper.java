package com.tech.api.mapper;

import com.tech.api.form.customer.*;
import com.tech.api.storage.model.CustomerAddress;
import com.tech.api.dto.customer.CustomerAddressDto;
import com.tech.api.dto.customer.CustomerAdminDto;
import com.tech.api.dto.customer.CustomerDto;
import com.tech.api.form.customer.*;
import com.tech.api.storage.model.Customer;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class}
)
public interface CustomerMapper {

    @Named("fromCustomerRegisterFormToEntity")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "username", target = "account.username")
    @Mapping(source = "password", target = "account.password", qualifiedByName = "passwordEncoder")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "fullName", target = "account.fullName")
    Customer fromCustomerRegisterFormToEntity(RegisterCustomerForm registerCustomerForm);

    @Named("fromCustomerCreateFormToEntity")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "status", target = "account.status")
    Customer fromCustomerCreateFormToEntity(CreateCustomerForm createCustomerForm);

    @Named("fromCustomerEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromEntityToAccountDtoMapper")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "walletMoney", target = "walletMoney")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "loyaltyLevel", target = "loyaltyLevel")
    @Mapping(source = "loyaltyPoint", target = "loyaltyPoint")
    @Mapping(source = "point", target = "point")
    CustomerDto fromCustomerEntityToDto(Customer customer);

    @Named("fromListCustomerEntityToListDtoMapper")
    @IterableMapping(elementTargetType = CustomerDto.class, qualifiedByName = "fromCustomerEntityToDtoMapper")
    List<CustomerDto> fromListCustomerEntityToListDto(List<Customer> customers);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account",qualifiedByName="accountAutoCompleteMapping")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "loyaltyLevel", target = "loyaltyLevel")
    @Mapping(source = "loyaltyPoint", target = "loyaltyPoint")
    @BeanMapping(ignoreByDefault = true)
    @Named("customerAutoCompleteMapping")
    CustomerDto fromEntityToAdminDtoAutoComplete(Customer customer);

    @Named("fromCustomerEntityToAdminDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "adminGetMapping")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "note", target = "note")
    CustomerAdminDto fromCustomerEntityToAdminDto(Customer customer);

    @Named("fromListCustomerEntityToListAdminDtoMapper")
    @IterableMapping(elementTargetType = CustomerAdminDto.class, qualifiedByName = "fromCustomerEntityToAdminDtoMapper")
    List<CustomerAdminDto> fromListCustomerEntityToListAdminDto(List<Customer> customers);

    @Named("fromUpdateCustomerFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "password", target = "account.password", qualifiedByName = "passwordEncoder")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "avatar", target = "account.avatarPath")
    @Mapping(source = "status", target = "account.status")
    void fromUpdateCustomerFormToEntity(UpdateCustomerForm updateProfileCustomerForm, @MappingTarget Customer customer);

    @Named("fromUpdateProfileCustomerFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "avatar", target = "account.avatarPath")
    @Mapping(source = "birthday", target = "birthday")
    void fromUpdateProfileCustomerFormToEntity(UpdateProfileCustomerForm updateProfileCustomerForm, @MappingTarget Customer customer);

    @Named("fromCustomerAddressEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(source = "note", target = "note")
    CustomerAddressDto fromCustomerAddressEntityToDto(CustomerAddress customerAddress);

    @Named("fromListCustomerAddressEntityToListDtoMapper")
    @IterableMapping(elementTargetType = CustomerAddressDto.class, qualifiedByName = "fromCustomerAddressEntityToDtoMapper")
    List<CustomerAddressDto> fromListCustomerAddressEntityToListDto(List<CustomerAddress> customerAddresses);

    @Named("fromCreateAddressFormToCustomerAddressMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    CustomerAddress fromCreateAddressFormToCustomerAddress(CreateAddressForm createAddressForm);

    @Named("fromUpdateAddressFormToCustomerAddressMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "receiverFullName", target = "receiverFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isDefault", target = "isDefault")
    void fromUpdateAddressFormToCustomerAddress(UpdateAddressForm updateAddressForm, @MappingTarget CustomerAddress customerAddress);
}
