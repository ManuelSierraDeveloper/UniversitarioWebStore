package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.OrderStatus;
import com.comercio.universitario.Entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCostumer_Id(Long costumerId);

    List<Order> findByCostumer_IdAndStateOrderAndDateBetween(
            Long costumerId,
            OrderStatus stateOrder,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("""
            select o
            from Order o
            where (:costumerId is null or o.costumer.id = :costumerId)
              and (:stateOrder is null or o.stateOrder = :stateOrder)
              and (:startDate is null or o.date >= :startDate)
              and (:endDate is null or o.date <= :endDate)
              and (:totalMin is null or o.total >= :totalMin)
              and (:totalMax is null or o.total <= :totalMax)
            order by o.date desc, o.idOrder desc
            """)
    List<Order> findByCombinedFilters(
            @Param("costumerId") Long costumerId,
            @Param("stateOrder") OrderStatus stateOrder,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("totalMin") Long totalMin,
            @Param("totalMax") Long totalMax
    );

    @Query("""
            select
                year(o.date) as year,
                month(o.date) as month,
                sum(o.total) as totalRevenue
            from Order o
            group by year(o.date), month(o.date)
            order by year(o.date), month(o.date)
            """)
    List<MonthlyRevenueView> findMonthlyRevenue();

    @Query("""
            select
                o.costumer as costumer,
                sum(o.total) as totalBilled
            from Order o
            group by o.costumer
            order by sum(o.total) desc
            """)
    List<TopCustomerBillingView> findTopCustomersByBilling();

    interface MonthlyRevenueView {
        Integer getYear();

        Integer getMonth();

        Long getTotalRevenue();
    }

    interface TopCustomerBillingView {
        Costumer getCostumer();

        Long getTotalBilled();
    }
}
