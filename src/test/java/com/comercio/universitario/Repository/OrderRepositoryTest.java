package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import com.comercio.universitario.Entitys.Enums.OrderStatus;
import com.comercio.universitario.Entitys.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CostomerRepository costomerRepository;

    @Test
    void shouldFindOrdersUsingComposedFilters() {
        Costumer juan = costomerRepository.save(Costumer.builder().fullName("Juan").state(CostumerStatus.ACTIVE).build());
        Costumer maria = costomerRepository.save(Costumer.builder().fullName("Maria").state(CostumerStatus.ACTIVE).build());

        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 10)).total(100L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.CREATED).date(LocalDate.of(2026, 1, 12)).total(200L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 2, 3)).total(300L).build());
        orderRepository.save(Order.builder().costumer(maria).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 15)).total(400L).build());

        List<Order> filteredOrders = orderRepository.findByCostumer_IdAndStateOrderAndDateBetween(
                juan.getId(),
                OrderStatus.PAID,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );

        assertThat(filteredOrders).hasSize(1);
        assertThat(filteredOrders.getFirst().getTotal()).isEqualTo(100L);
    }

    @Test
    void shouldFindOrdersByCustomer() {
        Costumer juan = costomerRepository.save(Costumer.builder().fullName("Juan").state(CostumerStatus.ACTIVE).build());
        Costumer maria = costomerRepository.save(Costumer.builder().fullName("Maria").state(CostumerStatus.ACTIVE).build());

        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 10)).total(100L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.CREATED).date(LocalDate.of(2026, 1, 12)).total(200L).build());
        orderRepository.save(Order.builder().costumer(maria).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 15)).total(400L).build());

        List<Order> juanOrders = orderRepository.findByCostumer_Id(juan.getId());

        assertThat(juanOrders).hasSize(2);
        assertThat(juanOrders).extracting(Order::getCostumer).allMatch(costumer -> costumer.getId().equals(juan.getId()));
    }

    @Test
    void shouldFindOrdersUsingAllCombinedFilters() {
        Costumer juan = costomerRepository.save(Costumer.builder().fullName("Juan").state(CostumerStatus.ACTIVE).build());

        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 10)).total(100L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 11)).total(450L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.CREATED).date(LocalDate.of(2026, 1, 12)).total(300L).build());
        orderRepository.save(Order.builder().costumer(juan).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 2, 1)).total(500L).build());

        List<Order> filtered = orderRepository.findByCombinedFilters(
                juan.getId(),
                OrderStatus.PAID,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31),
                200L,
                460L
        );

        assertThat(filtered).hasSize(1);
        assertThat(filtered.getFirst().getTotal()).isEqualTo(450L);
    }

    @Test
    void shouldCalculateMonthlyRevenue() {
        Costumer customer = costomerRepository.save(Costumer.builder().fullName("Cliente").state(CostumerStatus.ACTIVE).build());

        orderRepository.save(Order.builder().costumer(customer).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 10)).total(100L).build());
        orderRepository.save(Order.builder().costumer(customer).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 20)).total(250L).build());
        orderRepository.save(Order.builder().costumer(customer).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 2, 8)).total(300L).build());

        List<OrderRepository.MonthlyRevenueView> monthlyRevenue = orderRepository.findMonthlyRevenue();

        assertThat(monthlyRevenue).hasSize(2);
        assertThat(monthlyRevenue.get(0).getYear()).isEqualTo(2026);
        assertThat(monthlyRevenue.get(0).getMonth()).isEqualTo(1);
        assertThat(monthlyRevenue.get(0).getTotalRevenue()).isEqualTo(350L);
        assertThat(monthlyRevenue.get(1).getYear()).isEqualTo(2026);
        assertThat(monthlyRevenue.get(1).getMonth()).isEqualTo(2);
        assertThat(monthlyRevenue.get(1).getTotalRevenue()).isEqualTo(300L);
    }

    @Test
    void shouldFindTopCustomersByBilling() {
        Costumer premium = costomerRepository.save(Costumer.builder().fullName("Premium").state(CostumerStatus.ACTIVE).build());
        Costumer standard = costomerRepository.save(Costumer.builder().fullName("Standard").state(CostumerStatus.ACTIVE).build());

        orderRepository.save(Order.builder().costumer(premium).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 5)).total(400L).build());
        orderRepository.save(Order.builder().costumer(premium).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 7)).total(350L).build());
        orderRepository.save(Order.builder().costumer(standard).stateOrder(OrderStatus.PAID).date(LocalDate.of(2026, 1, 9)).total(300L).build());

        List<OrderRepository.TopCustomerBillingView> ranking = orderRepository.findTopCustomersByBilling();

        assertThat(ranking).hasSize(2);
        assertThat(ranking.getFirst().getCostumer().getFullName()).isEqualTo("Premium");
        assertThat(ranking.getFirst().getTotalBilled()).isEqualTo(750L);
        assertThat(ranking.get(1).getCostumer().getFullName()).isEqualTo("Standard");
        assertThat(ranking.get(1).getTotalBilled()).isEqualTo(300L);
    }
}
