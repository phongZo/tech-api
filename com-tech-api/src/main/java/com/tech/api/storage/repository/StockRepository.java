package com.tech.api.storage.repository;

import com.tech.api.dto.productvariant.VariantStockDto;
import com.tech.api.storage.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {
    Stock findFirstByProductVariantIdAndStoreId(Long productId, Long storeId);

    @Query("SELECT st.phone AS phone, st.addressDetails AS addressDetails, st.name AS name " +
            "FROM Stock s " +
            "JOIN ProductVariant v ON s.productVariant = v " +
            "JOIN Store st ON s.store = st " +
            "WHERE (s.total > 0 AND s.productVariant.id IN :variantList)")
    List<VariantStockDto> findAllStocksHaveVariant(@Param("variantList") List<Long> variantList);
}
