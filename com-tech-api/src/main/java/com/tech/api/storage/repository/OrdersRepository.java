package com.tech.api.storage.repository;

import com.tech.api.storage.model.Orders;
import com.tech.api.storage.projection.RevenueMonthly;
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
    @Query(value = "UPDATE tech_orders SET is_saved = true WHERE state = 3 OR state = 4",nativeQuery = true)
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

    @Query(value = "SELECT m.month, COALESCE(SUM(o.total_money), 0) AS revenue " +
            "FROM (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 " +
            "UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 " +
            "UNION SELECT 11 UNION SELECT 12) AS m " +
            "LEFT JOIN tech_orders o ON m.month = EXTRACT(MONTH FROM o.created_date) " +
            "AND EXTRACT(YEAR FROM o.created_date) = :year " +
            "AND o.state = 3 " +
            "AND (:storeId IS NULL OR o.store_id = :storeId)" +
            "GROUP BY m.month " +
            "ORDER BY m.month ASC",
            nativeQuery = true)
    List<RevenueMonthly> getRevenueMonthlyForManager(@Param("year") Integer year,
                                           @Param("storeId") Long storeId);

    @Query(value = "SELECT m.month, COALESCE(SUM(o.total_money), 0) AS revenue " +
            "FROM (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 " +
            "UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 " +
            "UNION SELECT 11 UNION SELECT 12) AS m " +
            "LEFT JOIN tech_orders o ON m.month = EXTRACT(MONTH FROM o.created_date) " +
            "AND EXTRACT(YEAR FROM o.created_date) = :year " +
            "AND o.state = 3 " +
            "GROUP BY m.month " +
            "ORDER BY m.month ASC",
            nativeQuery = true)
    List<RevenueMonthly> getRevenueMonthly(@Param("year") Integer year);
}

