package com.tech.api.mapper;

import com.tech.api.form.employee.CreateEmployeeForm;
import com.tech.api.form.employee.UpdateEmployeeForm;
import com.tech.api.dto.employee.EmployeeAdminDto;
import com.tech.api.dto.employee.EmployeeDto;
import com.tech.api.storage.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, StoreMapper.class}
)
public interface EmployeeMapper {

    @Named("fromCreateEmployeeFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "username", target = "account.username")
    @Mapping(source = "password", target = "account.password", qualifiedByName = "passwordEncoder")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "avatar", target = "account.avatarPath")
    @Mapping(source = "status", target = "account.status")
    @Mapping(source = "note", target = "note")
    Employee fromCreateEmployeeFormToEntity(CreateEmployeeForm createEmployeeForm);

    @Named("fromEmployeeEntityToDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromEntityToAccountDtoMapper")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "store", target = "storeDto", qualifiedByName = "fromStoreEntityToDto")
    EmployeeDto fromEmployeeEntityToDto(Employee employee);

    @Named("fromListEmployeeEntityToListDto")
    @IterableMapping(elementTargetType = EmployeeDto.class, qualifiedByName = "fromEmployeeEntityToDtoMapper")
    List<EmployeeDto> fromListEmployeeEntityToListDto(List<Employee> employees);

    @Named("fromEmployeeEntityToAdminDtoMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "adminGetMapping")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "store", target = "storeDto", qualifiedByName = "fromStoreEntityToDto")
    EmployeeAdminDto fromEmployeeEntityToAdminDto(Employee employee);

    @Named("fromEmployeeEntityListToAdminDtoListMapper")
    @IterableMapping(elementTargetType = EmployeeAdminDto.class, qualifiedByName = "fromEmployeeEntityToAdminDtoMapper")
    List<EmployeeAdminDto> fromEmployeeEntityListToAdminDtoList(List<Employee> employees);


    @Named("fromUpdateEmployeeFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "password", target = "account.password", qualifiedByName = "passwordEncoder")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "phone", target = "account.phone")
    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "avatar", target = "account.avatarPath")
    @Mapping(source = "status", target = "account.status")
    @Mapping(source = "note", target = "note")
    void fromUpdateEmployeeFormToEntity(UpdateEmployeeForm updateEmployeeForm, @MappingTarget Employee employee);
}
