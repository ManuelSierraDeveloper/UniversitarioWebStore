package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import com.comercio.universitario.Entitys.Enums.OrderStatus;
import com.comercio.universitario.Entitys.Order;
import com.comercio.universitario.Entitys.OrderItem;
import com.comercio.universitario.Entitys.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CostomerRepository costomerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindTopProductsSold() {
        Category category = categoryRepository.save(Category.builder().nameCategorie("Tecnologia").build());
        Product laptop = productRepository.save(Product.builder().nameProduct("Laptop").categorie(category).build());
        Product mouse = productRepository.save(Product.builder().nameProduct("Mouse").categorie(category).build());
        Product keyboard = productRepository.save(Product.builder().nameProduct("Keyboard").categorie(category).build());

        Costumer customer = costomerRepository.save(Costumer.builder().fullName("Cliente").state(CostumerStatus.ACTIVE).build());

        Order order1 = orderRepository.save(Order.builder().costumer(customer).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 3, 1)).total(200L).build());
        Order order2 = orderRepository.save(Order.builder().costumer(customer).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 3, 2)).total(300L).build());

        orderItemRepository.save(OrderItem.builder().order(order1).product(laptop).cantProduct(2).priceProduct(100L).build());
        orderItemRepository.save(OrderItem.builder().order(order1).product(mouse).cantProduct(1).priceProduct(20L).build());
        orderItemRepository.save(OrderItem.builder().order(order2).product(laptop).cantProduct(3).priceProduct(100L).build());
        orderItemRepository.save(OrderItem.builder().order(order2).product(keyboard).cantProduct(1).priceProduct(50L).build());

        List<OrderItemRepository.TopProductSoldView> topProducts = orderItemRepository.findTopProductsSold();

        assertThat(topProducts).hasSize(3);
        assertThat(topProducts.getFirst().getProduct().getNameProduct()).isEqualTo("Laptop");
        assertThat(topProducts.getFirst().getTotalSold()).isEqualTo(5L);
    }
}