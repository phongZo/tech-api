package com.tech.api.storage.repository;

import com.tech.api.storage.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    Store findByPosId(String posId);

    @Query("SELECT s FROM Store s" +
            " JOIN Stock st ON st.store = s" +
            " JOIN ProductVariant p ON st.productVariant = p" +
            " WHERE st.productVariant IN :variantList AND st.total > 0")
    List<Store> findAllByProvince(String province);
}
