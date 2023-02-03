package com.tech.api.mapper;

import com.tech.api.dto.customer.CustomerAddressDto;
import com.tech.api.dto.customer.CustomerAdminDto;
import com.tech.api.dto.customer.CustomerDto;
import com.tech.api.form.customer.CreateAddressForm;
import com.tech.api.form.customer.CreateCustomerForm;
import com.tech.api.form.customer.RegisterCustomerForm;
import com.tech.api.form.customer.UpdateAddressForm;
import com.tech.api.form.customer.UpdateCustomerForm;
import com.tech.api.form.customer.UpdateProfileCustomerForm;
import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.CustomerAddress;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T19:16:11+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Customer fromCustomerRegisterFormToEntity(RegisterCustomerForm registerCustomerForm) {
        if ( registerCustomerForm == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setAccount( registerCustomerFormToAccount( registerCustomerForm ) );
        customer.setBirthday( registerCustomerForm.getBirthday() );
        customer.setGender( registerCustomerForm.getGender() );

        return customer;
    }

    @Override
    public Customer fromCustomerCreateFormToEntity(CreateCustomerForm createCustomerForm) {
        if ( createCustomerForm == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setAccount( createCustomerFormToAccount( createCustomerForm ) );
        customer.setBirthday( createCustomerForm.getBirthday() );
        customer.setGender( createCustomerForm.getGender() );

        return customer;
    }

    @Override
    public CustomerDto fromCustomerEntityToDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setBirthday( customer.getBirthday() );
        customerDto.setNote( customer.getNote() );
        customerDto.setGender( customer.getGender() );
        customerDto.setId( customer.getId() );
        customerDto.setAccount( accountMapper.fromEntityToAccountDto( customer.getAccount() ) );
        customerDto.setWalletMoney( customer.getWalletMoney() );

        return customerDto;
    }

    @Override
    public List<CustomerDto> fromListCustomerEntityToListDto(List<Customer> customers) {
        if ( customers == null ) {
            return null;
        }

        List<CustomerDto> list = new ArrayList<CustomerDto>( customers.size() );
        for ( Customer customer : customers ) {
            list.add( fromCustomerEntityToDto( customer ) );
        }

        return list;
    }

    @Override
    public CustomerDto fromEntityToAdminDtoAutoComplete(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setBirthday( customer.getBirthday() );
        customerDto.setNote( customer.getNote() );
        customerDto.setGender( customer.getGender() );
        customerDto.setId( customer.getId() );
        customerDto.setAccount( accountMapper.fromEntityToAccountDtoAutoComplete( customer.getAccount() ) );

        return customerDto;
    }

    @Override
    public CustomerAdminDto fromCustomerEntityToAdminDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerAdminDto customerAdminDto = new CustomerAdminDto();

        customerAdminDto.setBirthday( customer.getBirthday() );
        customerAdminDto.setNote( customer.getNote() );
        customerAdminDto.setId( customer.getId() );
        customerAdminDto.setGender( customer.getGender() );
        customerAdminDto.setAccount( accountMapper.fromEntityToAccountAdminDto( customer.getAccount() ) );

        return customerAdminDto;
    }

    @Override
    public List<CustomerAdminDto> fromListCustomerEntityToListAdminDto(List<Customer> customers) {
        if ( customers == null ) {
            return null;
        }

        List<CustomerAdminDto> list = new ArrayList<CustomerAdminDto>( customers.size() );
        for ( Customer customer : customers ) {
            list.add( fromCustomerEntityToAdminDto( customer ) );
        }

        return list;
    }

    @Override
    public void fromUpdateCustomerFormToEntity(UpdateCustomerForm updateProfileCustomerForm, Customer customer) {
        if ( updateProfileCustomerForm == null ) {
            return;
        }

        if ( customer.getAccount() == null ) {
            customer.setAccount( new Account() );
        }
        updateCustomerFormToAccount( updateProfileCustomerForm, customer.getAccount() );
        if ( updateProfileCustomerForm.getBirthday() != null ) {
            customer.setBirthday( updateProfileCustomerForm.getBirthday() );
        }
        if ( updateProfileCustomerForm.getGender() != null ) {
            customer.setGender( updateProfileCustomerForm.getGender() );
        }
    }

    @Override
    public void fromUpdateProfileCustomerFormToEntity(UpdateProfileCustomerForm updateProfileCustomerForm, Customer customer) {
        if ( updateProfileCustomerForm == null ) {
            return;
        }

        if ( customer.getAccount() == null ) {
            customer.setAccount( new Account() );
        }
        updateProfileCustomerFormToAccount( updateProfileCustomerForm, customer.getAccount() );
        if ( updateProfileCustomerForm.getBirthday() != null ) {
            customer.setBirthday( updateProfileCustomerForm.getBirthday() );
        }
        if ( updateProfileCustomerForm.getGender() != null ) {
            customer.setGender( updateProfileCustomerForm.getGender() );
        }
    }

    @Override
    public CustomerAddressDto fromCustomerAddressEntityToDto(CustomerAddress customerAddress) {
        if ( customerAddress == null ) {
            return null;
        }

        CustomerAddressDto customerAddressDto = new CustomerAddressDto();

        customerAddressDto.setNote( customerAddress.getNote() );
        customerAddressDto.setAddressDetails( customerAddress.getAddressDetails() );
        customerAddressDto.setReceiverFullName( customerAddress.getReceiverFullName() );
        customerAddressDto.setIsDefault( customerAddress.getIsDefault() );
        customerAddressDto.setPhone( customerAddress.getPhone() );
        customerAddressDto.setId( customerAddress.getId() );

        return customerAddressDto;
    }

    @Override
    public List<CustomerAddressDto> fromListCustomerAddressEntityToListDto(List<CustomerAddress> customerAddresses) {
        if ( customerAddresses == null ) {
            return null;
        }

        List<CustomerAddressDto> list = new ArrayList<CustomerAddressDto>( customerAddresses.size() );
        for ( CustomerAddress customerAddress : customerAddresses ) {
            list.add( fromCustomerAddressEntityToDto( customerAddress ) );
        }

        return list;
    }

    @Override
    public CustomerAddress fromCreateAddressFormToCustomerAddress(CreateAddressForm createAddressForm) {
        if ( createAddressForm == null ) {
            return null;
        }

        CustomerAddress customerAddress = new CustomerAddress();

        customerAddress.setAddressDetails( createAddressForm.getAddressDetails() );
        customerAddress.setReceiverFullName( createAddressForm.getReceiverFullName() );
        customerAddress.setIsDefault( createAddressForm.getIsDefault() );
        customerAddress.setPhone( createAddressForm.getPhone() );

        return customerAddress;
    }

    @Override
    public void fromUpdateAddressFormToCustomerAddress(UpdateAddressForm updateAddressForm, CustomerAddress customerAddress) {
        if ( updateAddressForm == null ) {
            return;
        }

        if ( updateAddressForm.getAddressDetails() != null ) {
            customerAddress.setAddressDetails( updateAddressForm.getAddressDetails() );
        }
        if ( updateAddressForm.getReceiverFullName() != null ) {
            customerAddress.setReceiverFullName( updateAddressForm.getReceiverFullName() );
        }
        if ( updateAddressForm.getIsDefault() != null ) {
            customerAddress.setIsDefault( updateAddressForm.getIsDefault() );
        }
        if ( updateAddressForm.getPhone() != null ) {
            customerAddress.setPhone( updateAddressForm.getPhone() );
        }
    }

    protected Account registerCustomerFormToAccount(RegisterCustomerForm registerCustomerForm) {
        if ( registerCustomerForm == null ) {
            return null;
        }

        Account account = new Account();

        account.setEmail( registerCustomerForm.getEmail() );
        account.setUsername( registerCustomerForm.getUsername() );
        account.setFullName( registerCustomerForm.getFullName() );
        account.setPassword( accountMapper.encodePassword( registerCustomerForm.getPassword() ) );
        account.setPhone( registerCustomerForm.getPhone() );

        return account;
    }

    protected Account createCustomerFormToAccount(CreateCustomerForm createCustomerForm) {
        if ( createCustomerForm == null ) {
            return null;
        }

        Account account = new Account();

        account.setAvatarPath( createCustomerForm.getAvatar() );
        account.setEmail( createCustomerForm.getEmail() );
        account.setStatus( createCustomerForm.getStatus() );
        account.setUsername( createCustomerForm.getUsername() );
        account.setFullName( createCustomerForm.getFullName() );
        account.setPassword( accountMapper.encodePassword( createCustomerForm.getPassword() ) );
        account.setPhone( createCustomerForm.getPhone() );

        return account;
    }

    protected void updateCustomerFormToAccount(UpdateCustomerForm updateCustomerForm, Account mappingTarget) {
        if ( updateCustomerForm == null ) {
            return;
        }

        if ( updateCustomerForm.getAvatar() != null ) {
            mappingTarget.setAvatarPath( updateCustomerForm.getAvatar() );
        }
        if ( updateCustomerForm.getEmail() != null ) {
            mappingTarget.setEmail( updateCustomerForm.getEmail() );
        }
        if ( updateCustomerForm.getStatus() != null ) {
            mappingTarget.setStatus( updateCustomerForm.getStatus() );
        }
        if ( updateCustomerForm.getFullName() != null ) {
            mappingTarget.setFullName( updateCustomerForm.getFullName() );
        }
        if ( updateCustomerForm.getPassword() != null ) {
            mappingTarget.setPassword( accountMapper.encodePassword( updateCustomerForm.getPassword() ) );
        }
        if ( updateCustomerForm.getPhone() != null ) {
            mappingTarget.setPhone( updateCustomerForm.getPhone() );
        }
    }

    protected void updateProfileCustomerFormToAccount(UpdateProfileCustomerForm updateProfileCustomerForm, Account mappingTarget) {
        if ( updateProfileCustomerForm == null ) {
            return;
        }

        if ( updateProfileCustomerForm.getAvatar() != null ) {
            mappingTarget.setAvatarPath( updateProfileCustomerForm.getAvatar() );
        }
        if ( updateProfileCustomerForm.getFullName() != null ) {
            mappingTarget.setFullName( updateProfileCustomerForm.getFullName() );
        }
    }
}
