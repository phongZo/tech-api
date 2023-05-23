package com.tech.api.mapper;

import com.tech.api.form.orders.CreateOrdersClientForm;
import com.tech.api.form.orders.CreateOrdersForm;
import com.tech.api.storage.model.Orders;
import com.tech.api.dto.orders.OrdersDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerMapper.class, CustomerAddressMapper.class})
public interface OrdersMapper {
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @BeanMapping(ignoreByDefault = true)
    @Named("clientCreateMapping")
    Orders fromClientCreateOrdersFormToEntity(CreateOrdersClientForm createOrdersForm);

    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(source = "isDelivery", target = "isDelivery")
    @BeanMapping(ignoreByDefault = true)
    @Named("clientCreateMapping")
    Orders fromCreateOrdersFormToEntity(CreateOrdersForm createOrdersForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "totalMoney", target = "totalMoney")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "prevState", target = "prevState")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "saleOffMoney", target = "saleOffMoney")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "expectedReceiveDate", target = "expectedReceiveDate")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "isPaid", target = "isPaid")
    @Mapping(source = "isDelivery", target = "isDelivery")
    @Mapping(source = "address", target = "customerAddressDto", qualifiedByName = "clientGetCustomerAddress")
    @BeanMapping(ignoreByDefault = true)
    @Named("getMapping")
    OrdersDto fromEntityToOrdersDto(Orders orders);

    @IterableMapping(elementTargetType = OrdersDto.class, qualifiedByName = "getMapping")
    List<OrdersDto> fromEntityListToOrdersDtoList(List<Orders> ordersList);
}
