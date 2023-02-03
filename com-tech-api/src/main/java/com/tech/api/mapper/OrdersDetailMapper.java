package com.tech.api.mapper;

import com.tech.api.dto.orders.OrdersDetailDto;
import com.tech.api.form.orders.CreateOrdersDetailForm;
import com.tech.api.storage.model.OrdersDetail;
import com.tech.api.form.orders.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrdersMapper.class,ProductMapper.class,ProductVariantMapper.class})
public interface OrdersDetailMapper {

    @Mapping(source = "variantId", target = "productVariant.id")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "note", target = "note")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    OrdersDetail fromCreateOrdersDetailFormToEntity(CreateOrdersDetailForm createOrdersDetailForm);

    @IterableMapping(elementTargetType = OrdersDetail.class, qualifiedByName = "adminCreateMapping")
    List<OrdersDetail> fromCreateOrdersDetailFormListToOrdersDetailList(List<CreateOrdersDetailForm> createOrdersDetailFormList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "productVariant", target = "productVariantDto", qualifiedByName = "fromProductVariantEntityToDtoAutoComplete")
    @Mapping(source = "productVariant.productConfig.product", target = "productDto", qualifiedByName = "clientGetMapping")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "isReviewed", target = "isReviewed")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    OrdersDetailDto fromEntityToOrdersDetailDto(OrdersDetail ordersDetail);

    @IterableMapping(elementTargetType = OrdersDetailDto.class, qualifiedByName = "adminGetMapping")
    List<OrdersDetailDto> fromEntityListToOrdersDetailDtoList(List<OrdersDetail> ordersDetailList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "productVariant.productConfig.product", target = "productDto", qualifiedByName = "clientGetMapping")
    @Mapping(source = "productVariant", target = "productVariantDto", qualifiedByName = "fromProductVariantEntityToDtoAutoComplete")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "amount", target = "amount")
    @BeanMapping(ignoreByDefault = true)
    @Named("clientGetMapping")
    OrdersDetailDto fromEntityToOrdersDetailClientDto(OrdersDetail ordersDetail);

    @IterableMapping(elementTargetType = OrdersDetailDto.class, qualifiedByName = "clientGetMapping")
    List<OrdersDetailDto> fromEntityListToOrdersDetailClientDtoList(List<OrdersDetail> ordersDetailList);
}
