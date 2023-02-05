package com.tech.api.mapper;

import com.tech.api.dto.customer.CustomerAddressDto;
import com.tech.api.form.customer.CreateAddressForm;
import com.tech.api.form.customer.UpdateAddressForm;
import com.tech.api.storage.model.CustomerAddress;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:03+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CustomerAddressMapperImpl implements CustomerAddressMapper {

    @Override
    public CustomerAddressDto fromEntityToDto(CustomerAddress address) {
        if ( address == null ) {
            return null;
        }

        CustomerAddressDto customerAddressDto = new CustomerAddressDto();

        customerAddressDto.setNote( address.getNote() );
        customerAddressDto.setAddressDetails( address.getAddressDetails() );
        customerAddressDto.setReceiverFullName( address.getReceiverFullName() );
        customerAddressDto.setIsDefault( address.getIsDefault() );
        customerAddressDto.setCreatedDate( address.getCreatedDate() );
        customerAddressDto.setTypeAddress( address.getTypeAddress() );
        customerAddressDto.setPhone( address.getPhone() );
        customerAddressDto.setCreatedBy( address.getCreatedBy() );
        customerAddressDto.setModifiedDate( address.getModifiedDate() );
        customerAddressDto.setModifiedBy( address.getModifiedBy() );
        customerAddressDto.setId( address.getId() );
        customerAddressDto.setStatus( address.getStatus() );

        return customerAddressDto;
    }

    @Override
    public List<CustomerAddressDto> fromEntityListToAddressDto(List<CustomerAddress> addressList) {
        if ( addressList == null ) {
            return null;
        }

        List<CustomerAddressDto> list = new ArrayList<CustomerAddressDto>( addressList.size() );
        for ( CustomerAddress customerAddress : addressList ) {
            list.add( fromEntityToDto( customerAddress ) );
        }

        return list;
    }

    @Override
    public CustomerAddress fromCreateFormToEntity(CreateAddressForm address) {
        if ( address == null ) {
            return null;
        }

        CustomerAddress customerAddress = new CustomerAddress();

        customerAddress.setAddressDetails( address.getAddressDetails() );
        customerAddress.setReceiverFullName( address.getReceiverFullName() );
        customerAddress.setIsDefault( address.getIsDefault() );
        customerAddress.setTypeAddress( address.getTypeAddress() );
        customerAddress.setPhone( address.getPhone() );

        return customerAddress;
    }

    @Override
    public void fromUpdateFormToEntity(UpdateAddressForm address, CustomerAddress customerAddress) {
        if ( address == null ) {
            return;
        }

        if ( address.getAddressDetails() != null ) {
            customerAddress.setAddressDetails( address.getAddressDetails() );
        }
        if ( address.getReceiverFullName() != null ) {
            customerAddress.setReceiverFullName( address.getReceiverFullName() );
        }
        if ( address.getIsDefault() != null ) {
            customerAddress.setIsDefault( address.getIsDefault() );
        }
        if ( address.getTypeAddress() != null ) {
            customerAddress.setTypeAddress( address.getTypeAddress() );
        }
        if ( address.getPhone() != null ) {
            customerAddress.setPhone( address.getPhone() );
        }
    }
}
