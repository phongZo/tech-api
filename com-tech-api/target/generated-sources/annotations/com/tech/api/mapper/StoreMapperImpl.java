package com.tech.api.mapper;

import com.tech.api.dto.store.StoreDto;
import com.tech.api.form.store.CreateStoreForm;
import com.tech.api.form.store.UpdateStoreForm;
import com.tech.api.storage.model.Store;
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
public class StoreMapperImpl implements StoreMapper {

    @Override
    public Store fromCreateStoreFormToEntity(CreateStoreForm createStoreForm) {
        if ( createStoreForm == null ) {
            return null;
        }

        Store store = new Store();

        store.setLatitude( createStoreForm.getLatitude() );
        store.setAddressDetails( createStoreForm.getAddressDetails() );
        store.setSessionId( createStoreForm.getSessionId() );
        store.setPosId( createStoreForm.getPosId() );
        store.setIsAcceptOrder( createStoreForm.getIsAcceptOrder() );
        store.setName( createStoreForm.getName() );
        store.setLongitude( createStoreForm.getLongitude() );

        return store;
    }

    @Override
    public StoreDto fromStoreEntityToDto(Store store) {
        if ( store == null ) {
            return null;
        }

        StoreDto storeDto = new StoreDto();

        storeDto.setIsAcceptOrder( store.getIsAcceptOrder() );
        storeDto.setLatitude( store.getLatitude() );
        storeDto.setName( store.getName() );
        storeDto.setAddressDetails( store.getAddressDetails() );
        storeDto.setId( store.getId() );
        storeDto.setLongitude( store.getLongitude() );

        return storeDto;
    }

    @Override
    public List<StoreDto> fromStoreEntityListToDtoList(List<Store> storeList) {
        if ( storeList == null ) {
            return null;
        }

        List<StoreDto> list = new ArrayList<StoreDto>( storeList.size() );
        for ( Store store : storeList ) {
            list.add( fromStoreEntityToDto( store ) );
        }

        return list;
    }

    @Override
    public void fromUpdateStoreFormToEntity(UpdateStoreForm updateStoreForm, Store store) {
        if ( updateStoreForm == null ) {
            return;
        }

        if ( updateStoreForm.getLatitude() != null ) {
            store.setLatitude( updateStoreForm.getLatitude() );
        }
        if ( updateStoreForm.getAddressDetails() != null ) {
            store.setAddressDetails( updateStoreForm.getAddressDetails() );
        }
        if ( updateStoreForm.getName() != null ) {
            store.setName( updateStoreForm.getName() );
        }
        if ( updateStoreForm.getLongitude() != null ) {
            store.setLongitude( updateStoreForm.getLongitude() );
        }
    }
}
