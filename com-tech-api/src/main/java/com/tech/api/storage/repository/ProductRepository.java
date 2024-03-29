package com.tech.api.storage.repository;

import com.tech.api.dto.product.ProductDto;
import com.tech.api.storage.model.Product;
import com.tech.api.storage.model.ProductVariant;
import com.tech.api.storage.projection.ProductOrdersDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p.id as id, p.name as name,  COALESCE(SUM(CASE WHEN (o.state = 3) THEN (od.price * od.amount) ELSE 0 END), 0) as revenue,  COALESCE(SUM(CASE WHEN (o.state = 3) THEN od.amount ELSE 0 END), 0) as amount" +
            " FROM Product p" +
            " LEFT JOIN ProductConfig c ON p = c.product" +
            " LEFT JOIN ProductVariant variant ON variant.productConfig = c" +
            " LEFT JOIN OrdersDetail od ON variant = od.productVariant" +
            " LEFT JOIN Orders  o ON od.orders.id = o.id" +
            " WHERE (cast(:fromDate as date) IS NULL OR o.createdDate >= :fromDate)" +
            " AND (cast(:toDate as date) IS NULL OR o.createdDate <= :toDate)" +
            " AND (cast(:storeId as long) IS NULL OR o.store.id = :storeId)" +
            " group by p.id,p.name" +
            " ORDER BY revenue DESC")
    Page<ProductOrdersDetail> findAllProductAndRevenue(@Param("fromDate") Date fromDate,
                                                       @Param("toDate") Date toDate,
                                                       @Param("storeId") Long storeId,
                                                       Pageable pageable);



    @Query("SELECT new com.tech.api.dto.product.ProductDto(p.id, p.saleOff, p.isSaleOff, p.name, p.price, COALESCE(CAST(SUM(s.total) AS int),0), p.isSoldOut, p.image) " +
            "FROM Product p " +
            "LEFT JOIN ProductConfig c ON c.product = p " +
            "LEFT JOIN ProductVariant v ON v.productConfig = c " +
            "LEFT JOIN Stock s ON s.productVariant = v " +
            "WHERE s.store.id = :storeId " +
            "AND p.status = 1 " +
            "GROUP BY p.id")
    Page<ProductDto> findAllProductInStockOfStore(@Param("storeId") Long storeId, Pageable pageable);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id in :list")
    Page<Product> findAllByListId(@Param("list") List<Long> list, Pageable pageable);
}
