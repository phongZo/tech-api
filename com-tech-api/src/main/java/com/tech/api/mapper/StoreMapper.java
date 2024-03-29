package com.tech.api.mapper;

import com.tech.api.dto.store.StoreDto;
import com.tech.api.form.store.CreateStoreForm;
import com.tech.api.form.store.UpdateStoreForm;
import com.tech.api.storage.model.Store;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StoreMapper {

    @Named("fromCreateStoreFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    Store fromCreateStoreFormToEntity(CreateStoreForm createStoreForm);

    @Named("fromStoreEntityToDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "shopId", target = "shopId")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    @Mapping(source = "status", target = "status")
    StoreDto fromStoreEntityToDto(Store store);

    @Named("fromStoreEntityListToDtoListMapper")
    @IterableMapping(elementTargetType = StoreDto.class, qualifiedByName = "fromStoreEntityToDto")
    List<StoreDto> fromStoreEntityListToDtoList(List<Store> storeList);

    @Named("fromUpdateStoreFormToEntityMapper")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "addressDetails", target = "addressDetails")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "provinceCode", target = "provinceCode")
    @Mapping(source = "districtCode", target = "districtCode")
    @Mapping(source = "wardCode", target = "wardCode")
    void fromUpdateStoreFormToEntity(UpdateStoreForm updateStoreForm, @MappingTarget Store store);
}
