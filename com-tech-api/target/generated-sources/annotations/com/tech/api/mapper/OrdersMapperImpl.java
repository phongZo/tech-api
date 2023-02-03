package com.tech.api.mapper;

import com.tech.api.dto.orders.OrdersDto;
import com.tech.api.form.orders.CreateOrdersClientForm;
import com.tech.api.storage.model.Orders;
import com.tech.api.storage.model.Store;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T19:16:10+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class OrdersMapperImpl implements OrdersMapper {

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public Orders fromCreateOrdersFormToEntity(CreateOrdersClientForm createOrdersForm) {
        if ( createOrdersForm == null ) {
            return null;
        }

        Orders orders = new Orders();

        orders.setDeliveryFee( createOrdersForm.getDeliveryFee() );
        orders.setPaymentMethod( createOrdersForm.getPaymentMethod() );
        if ( createOrdersForm.getSaleOff() != null ) {
            orders.setSaleOff( createOrdersForm.getSaleOff().intValue() );
        }

        return orders;
    }

    @Override
    public OrdersDto fromEntityToOrdersDto(Orders orders) {
        if ( orders == null ) {
            return null;
        }

        OrdersDto ordersDto = new OrdersDto();

        ordersDto.setNote( orders.getNote() );
        ordersDto.setAmount( orders.getAmount() );
        ordersDto.setCode( orders.getCode() );
        ordersDto.setTotalMoney( orders.getTotalMoney() );
        ordersDto.setCustomerAddressDto( customerAddressMapper.fromEntityToDto( orders.getAddress() ) );
        ordersDto.setStoreId( ordersStoreId( orders ) );
        ordersDto.setSaleOffMoney( orders.getSaleOffMoney() );
        ordersDto.setPrevState( orders.getPrevState() );
        ordersDto.setCreatedDate( orders.getCreatedDate() );
        ordersDto.setDeliveryFee( orders.getDeliveryFee() );
        ordersDto.setExpectedReceiveDate( orders.getExpectedReceiveDate() );
        ordersDto.setPaymentMethod( orders.getPaymentMethod() );
        ordersDto.setId( orders.getId() );
        ordersDto.setSaleOff( orders.getSaleOff() );
        ordersDto.setState( orders.getState() );

        return ordersDto;
    }

    @Override
    public List<OrdersDto> fromEntityListToOrdersDtoList(List<Orders> ordersList) {
        if ( ordersList == null ) {
            return null;
        }

        List<OrdersDto> list = new ArrayList<OrdersDto>( ordersList.size() );
        for ( Orders orders : ordersList ) {
            list.add( fromEntityToOrdersDto( orders ) );
        }

        return list;
    }

    private Long ordersStoreId(Orders orders) {
        if ( orders == null ) {
            return null;
        }
        Store store = orders.getStore();
        if ( store == null ) {
            return null;
        }
        Long id = store.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
