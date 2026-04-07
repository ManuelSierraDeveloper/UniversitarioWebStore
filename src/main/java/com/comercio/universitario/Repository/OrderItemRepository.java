package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.OrderItem;
import com.comercio.universitario.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    interface TopProductSoldView {
        Product getProduct();

        Long getTotalSold();
    }
}