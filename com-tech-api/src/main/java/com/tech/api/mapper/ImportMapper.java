package com.tech.api.mapper;

import com.tech.api.dto.importDto.ImportDto;
import com.tech.api.dto.importDto.ImportLineItemDto;
import com.tech.api.dto.orders.OrdersDto;
import com.tech.api.form.orders.CreateOrdersClientForm;
import com.tech.api.storage.model.Import;
import com.tech.api.storage.model.ImportLineItem;
import com.tech.api.storage.model.Orders;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {StoreMapper.class, ProductVariantMapper.class})
public interface ImportMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "variant", target = "variantDto")
    @Mapping(source = "quantity", target = "quantity", qualifiedByName = "fromProductVariantEntityToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToImportLineItemDto")
    ImportLineItemDto fromEntityToImportLineItemDto(ImportLineItem importLineItem);

    @IterableMapping(elementTargetType = ImportLineItemDto.class, qualifiedByName = "fromEntityToImportLineItemDto")
    List<ImportLineItemDto> fromEntityListToImportLineItemDtoList(List<ImportLineItem> importList);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "store", target = "storeDto", qualifiedByName = "fromStoreEntityToDto")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "createdBy", target = "createdBy")
    @BeanMapping(ignoreByDefault = true)
    @Named("getMapping")
    ImportDto fromEntityToImportDto(Import importData);

    @IterableMapping(elementTargetType = ImportDto.class, qualifiedByName = "getMapping")
    List<ImportDto> fromEntityListToImportDtoList(List<Import> importList);
}
