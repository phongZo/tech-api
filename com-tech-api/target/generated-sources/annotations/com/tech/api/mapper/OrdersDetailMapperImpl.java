package com.tech.api.mapper;

import com.tech.api.dto.orders.OrdersDetailDto;
import com.tech.api.form.orders.CreateOrdersDetailForm;
import com.tech.api.storage.model.OrdersDetail;
import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductConfig;
import com.tech.api.storage.model.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:03+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class OrdersDetailMapperImpl implements OrdersDetailMapper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;

    @Override
    public OrdersDetail fromCreateOrdersDetailFormToEntity(CreateOrdersDetailForm createOrdersDetailForm) {
        if ( createOrdersDetailForm == null ) {
            return null;
        }

        OrdersDetail ordersDetail = new OrdersDetail();

        ordersDetail.setProductVariant( createOrdersDetailFormToProductVariant( createOrdersDetailForm ) );
        ordersDetail.setNote( createOrdersDetailForm.getNote() );
        ordersDetail.setAmount( createOrdersDetailForm.getAmount() );

        return ordersDetail;
    }

    @Override
    public List<OrdersDetail> fromCreateOrdersDetailFormListToOrdersDetailList(List<CreateOrdersDetailForm> createOrdersDetailFormList) {
        if ( createOrdersDetailFormList == null ) {
            return null;
        }

        List<OrdersDetail> list = new ArrayList<OrdersDetail>( createOrdersDetailFormList.size() );
        for ( CreateOrdersDetailForm createOrdersDetailForm : createOrdersDetailFormList ) {
            list.add( fromCreateOrdersDetailFormToEntity( createOrdersDetailForm ) );
        }

        return list;
    }

    @Override
    public OrdersDetailDto fromEntityToOrdersDetailDto(OrdersDetail ordersDetail) {
        if ( ordersDetail == null ) {
            return null;
        }

        OrdersDetailDto ordersDetailDto = new OrdersDetailDto();

        ordersDetailDto.setAmount( ordersDetail.getAmount() );
        ordersDetailDto.setIsReviewed( ordersDetail.getIsReviewed() );
        ordersDetailDto.setProductDto( productMapper.fromEntityToClientDto( ordersDetailProductVariantProductConfigProduct( ordersDetail ) ) );
        ordersDetailDto.setProductVariantDto( productVariantMapper.fromProductVariantEntityToDtoAutoComplete( ordersDetail.getProductVariant() ) );
        ordersDetailDto.setPrice( ordersDetail.getPrice() );
        ordersDetailDto.setId( ordersDetail.getId() );

        return ordersDetailDto;
    }

    @Override
    public List<OrdersDetailDto> fromEntityListToOrdersDetailDtoList(List<OrdersDetail> ordersDetailList) {
        if ( ordersDetailList == null ) {
            return null;
        }

        List<OrdersDetailDto> list = new ArrayList<OrdersDetailDto>( ordersDetailList.size() );
        for ( OrdersDetail ordersDetail : ordersDetailList ) {
            list.add( fromEntityToOrdersDetailDto( ordersDetail ) );
        }

        return list;
    }

    @Override
    public OrdersDetailDto fromEntityToOrdersDetailClientDto(OrdersDetail ordersDetail) {
        if ( ordersDetail == null ) {
            return null;
        }

        OrdersDetailDto ordersDetailDto = new OrdersDetailDto();

        ordersDetailDto.setAmount( ordersDetail.getAmount() );
        ordersDetailDto.setProductDto( productMapper.fromEntityToClientDto( ordersDetailProductVariantProductConfigProduct( ordersDetail ) ) );
        ordersDetailDto.setProductVariantDto( productVariantMapper.fromProductVariantEntityToDtoAutoComplete( ordersDetail.getProductVariant() ) );
        ordersDetailDto.setPrice( ordersDetail.getPrice() );
        ordersDetailDto.setId( ordersDetail.getId() );

        return ordersDetailDto;
    }

    @Override
    public List<OrdersDetailDto> fromEntityListToOrdersDetailClientDtoList(List<OrdersDetail> ordersDetailList) {
        if ( ordersDetailList == null ) {
            return null;
        }

        List<OrdersDetailDto> list = new ArrayList<OrdersDetailDto>( ordersDetailList.size() );
        for ( OrdersDetail ordersDetail : ordersDetailList ) {
            list.add( fromEntityToOrdersDetailClientDto( ordersDetail ) );
        }

        return list;
    }

    protected ProductVariant createOrdersDetailFormToProductVariant(CreateOrdersDetailForm createOrdersDetailForm) {
        if ( createOrdersDetailForm == null ) {
            return null;
        }

        ProductVariant productVariant = new ProductVariant();

        productVariant.setId( createOrdersDetailForm.getVariantId() );

        return productVariant;
    }

    private Product ordersDetailProductVariantProductConfigProduct(OrdersDetail ordersDetail) {
        if ( ordersDetail == null ) {
            return null;
        }
        ProductVariant productVariant = ordersDetail.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        ProductConfig productConfig = productVariant.getProductConfig();
        if ( productConfig == null ) {
            return null;
        }
        Product product = productConfig.getProduct();
        if ( product == null ) {
            return null;
        }
        return product;
    }
}
