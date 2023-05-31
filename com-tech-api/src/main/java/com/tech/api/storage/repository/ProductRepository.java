package com.tech.api.storage.repository;

import com.tech.api.storage.model.Product;
import com.tech.api.storage.projection.ProductOrdersDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p.id as id, p.name as name,  COALESCE(SUM(CASE WHEN (o.state = 3) THEN SUM(od.price * od.amount) ELSE 0 END), 0) as revenue,  COALESCE(SUM(od.amount), 0) as amount" +
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
}
