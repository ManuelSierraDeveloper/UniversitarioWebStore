package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.OrderItem;
import com.comercio.universitario.Entitys.Category;
import com.comercio.universitario.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
            select
                oi.product as product,
                sum(oi.cantProduct) as totalSold
            from OrderItem oi
            group by oi.product
            order by sum(oi.cantProduct) desc
            """)
    List<TopProductSoldView> findTopProductsSold();

    @Query("""
            select
                oi.product as product,
                sum(oi.cantProduct) as totalSold
            from OrderItem oi
            where oi.order.date between :startDate and :endDate
            group by oi.product
            order by sum(oi.cantProduct) desc
            """)
    List<TopProductSoldView> findTopProductsSoldByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
            select
                oi.product.categorie as category,
                sum(oi.cantProduct) as totalSold
            from OrderItem oi
            group by oi.product.categorie
            order by sum(oi.cantProduct) desc
            """)
    List<TopCategoryBySalesVolumeView> findTopCategoriesBySalesVolume();

    interface TopProductSoldView {
        Product getProduct();

        Long getTotalSold();
    }

    interface TopCategoryBySalesVolumeView {
        Category getCategory();

        Long getTotalSold();
    }
}
