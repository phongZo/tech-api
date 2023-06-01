package com.tech.api.storage.repository;

import com.tech.api.dto.productvariant.ProductVariantDto;
import com.tech.api.storage.model.ProductVariant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>, JpaSpecificationExecutor<ProductVariant> {
    @Query("SELECT new com.tech.api.dto.productvariant.ProductVariantDto(v.id, v.name, v.price, COALESCE(CAST(SUM(s.total) AS int),0)) " +
            "FROM ProductVariant v " +
            "LEFT JOIN ProductConfig c ON v.productConfig = c " +
            "LEFT JOIN Product p ON c.product = p " +
            "LEFT JOIN Stock s ON s.productVariant = v " +
            "WHERE s.store.id = :storeId " +
            "AND p.id = :productId " +
            "AND v.status = 1 " +
            "GROUP BY v.id")
    List<ProductVariantDto> findAllVariantInStockOfStore(@Param("storeId") Long storeId, @Param("productId") Long productId);
}
