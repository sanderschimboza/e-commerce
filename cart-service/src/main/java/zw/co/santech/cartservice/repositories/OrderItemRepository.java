package zw.co.santech.cartservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.cartservice.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
