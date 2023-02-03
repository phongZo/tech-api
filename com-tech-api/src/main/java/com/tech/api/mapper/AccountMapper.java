package com.tech.api.mapper;

import com.tech.api.form.account.UpdateAccountAdminForm;
import com.tech.api.storage.model.Account;
import com.tech.api.dto.account.AccountAdminDto;
import com.tech.api.dto.account.AccountDto;
import com.tech.api.form.account.CreateAccountAdminForm;
import com.tech.api.form.account.UpdateProfileAdminForm;
import org.mapstruct.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "phone", target = "phone")
    Account fromCreateAccountAdminFormToAdmin(CreateAccountAdminForm createAccountAdminForm);


    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "phone", target = "phone")
    @BeanMapping(ignoreByDefault = true)
    void mappingFormUpdateAdminToEntity(UpdateAccountAdminForm updateAccountAdminForm, @MappingTarget Account account);

    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    void mappingFormUpdateProfileToEntity(UpdateProfileAdminForm updateProfileAdminForm, @MappingTarget Account account);

    @Named("fromEntityToAccountDtoMapper")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    AccountDto fromEntityToAccountDto(Account account);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "phone", target = "phone")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    AccountAdminDto fromEntityToAccountAdminDto(Account account);

    @IterableMapping(elementTargetType = AccountAdminDto.class, qualifiedByName = "adminGetMapping")
    List<AccountAdminDto> fromEntityListToDtoList(List<Account> content);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminAutoCompleteMapping")
    AccountAdminDto fromEntityToAccountAdminDtoAutoComplete(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    @Named("accountAutoCompleteMapping")
    AccountDto fromEntityToAccountDtoAutoComplete(Account account);

    @Named("passwordEncoder")
    default String encodePassword(String password) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
    }
}
