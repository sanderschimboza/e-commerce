package zw.co.santech.cartservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.cartservice.enums.OrderStatus;
import zw.co.santech.cartservice.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUserId(String userId);
    List<Order> findOrderByOrderNumber(Long orderNumber);
    List<Order> findOrderByOrderStatus(OrderStatus orderStatus);
}
