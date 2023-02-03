package com.tech.api.mapper;

import com.tech.api.dto.cart.CartDto;
import com.tech.api.dto.cart.LineItemDto;
import com.tech.api.storage.model.Cart;
import com.tech.api.storage.model.LineItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductVariantMapper.class}
)
public interface CartMapper {

    @Named("fromEntityToCartDtoMapper")
    @Mapping(source = "id", target = "id")
    CartDto fromEntityToCartDto(Cart cart);



    // --------------------------------------LineItem--------------------------------------
    @Named("fromEntityToLineItemDtoMapper")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "variant", target = "productVariantDto", qualifiedByName = "fromProductVariantEntityToDtoMapper")
    @Mapping(source = "quantity", target = "quantity")
    LineItemDto fromEntityToLineItemDto(LineItem lineItem);
}
