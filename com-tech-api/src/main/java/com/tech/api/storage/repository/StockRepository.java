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

    @Query("SELECT s" +
            " FROM Stock s" +
            " WHERE s.store.id = :storeId" +
            " AND s.productVariant.id IN :variantList" +
            " AND s.total > 0")
    List<Stock> findAllByListProductVariantIdAndStoreId(@Param("variantList") List<Long> variantList,
                                                        @Param("storeId") Long storeId);

    @Query("SELECT new com.tech.api.dto.productvariant.VariantStockDto(st.addressDetails,st.name,st.phone,COALESCE(s.total,0),st.id) " +
            "FROM Store st " +
            "LEFT JOIN Stock s ON s.store = st AND s.productVariant.id = :id " +
            "GROUP BY st.name, st.id, s.total")
    List<VariantStockDto> findAllTotalInStockOfStore(@Param("id") Long id);

    @Query("SELECT new com.tech.api.dto.productvariant.VariantStockDto(st.addressDetails,st.name,st.phone,s.total,st.id) " +
            "FROM Stock s " +
            "JOIN ProductVariant v ON s.productVariant = v " +
            "JOIN Store st ON s.store = st " +
            "WHERE (s.total > 0 AND s.productVariant.id IN :variantList)")
    List<VariantStockDto> findAllStocksHaveVariant(@Param("variantList") List<Long> variantList);
}
