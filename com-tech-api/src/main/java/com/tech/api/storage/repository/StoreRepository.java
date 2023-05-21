package com.tech.api.storage.repository;

import com.tech.api.storage.model.Store;
import com.tech.api.storage.projection.ProductOrdersDetail;
import com.tech.api.storage.projection.StoreRevenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    Store findByPosId(String posId);

    @Query("SELECT s FROM Store s" +
            " JOIN Stock st ON st.store = s" +
            " JOIN ProductVariant p ON st.productVariant = p" +
            " WHERE st.productVariant IN :variantList AND st.total > 0")
    List<Store> findAllByProvince(String province);

    List<Store> findAllByProvinceCode(Long id);

    @Query("SELECT s.id as id, s.name as name,  COALESCE(SUM(CASE WHEN (o.state = 3) THEN (od.price * od.amount) ELSE 0 END), 0) as revenue,  COALESCE(COUNT(o), 0) as orders" +
            " FROM Store s" +
            " LEFT JOIN Orders o ON o.store.id = s.id" +
            " LEFT JOIN OrdersDetail od ON od.orders.id = o.id" +
            " WHERE (cast(:fromDate as date) IS NULL OR o.createdDate >= :fromDate)" +
            " AND (cast(:toDate as date) IS NULL OR o.createdDate <= :toDate)" +
            " group by s.id,s.name" +
            " ORDER BY revenue desc")
    Page<StoreRevenue> findAllStoreAndRevenue(@Param("fromDate") Date fromDate,
                                                @Param("toDate") Date toDate,
                                                Pageable pageable);
}
