package com.tech.api.storage.repository;

import com.tech.api.storage.model.Orders;
import com.tech.api.storage.projection.RevenueOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    Orders findOrdersByIdAndCustomerId(Long ordersId, Long customerId);
    @Query("SELECT sum(o.totalMoney) FROM Orders o WHERE o IN :list GROUP BY o.id")
    Double sumMoney(@Param("list") List<Orders> orders);

    @Query("SELECT MAX(o.id) FROM Orders o")
    Long findMaxId();

    @Transactional()
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tech_orders SET state = 5 WHERE state = 3 OR state = 4",nativeQuery = true)
    void updateArchive();

    @Query("SELECT COUNT(o.id) as totalOrders, COALESCE(SUM(o.totalMoney - o.deliveryFee),0) AS revenue, COALESCE(SUM(o.saleOffMoney),0) AS discount" +
            " FROM Orders o" +
            " WHERE o.state = 3" +
            " AND (cast(:storeId as long) IS NULL OR o.store.id = :storeId)" +
            " AND (cast(:fromDate as date) IS NULL OR o.createdDate >= :fromDate)" +
            " AND (cast(:toDate as date) IS NULL OR o.createdDate <= :toDate)")
    RevenueOrders getRevenueOrders(@Param("fromDate") Date fromDate,
                                   @Param("toDate") Date toDate,
                                   @Param("storeId") Long storeId);
}

