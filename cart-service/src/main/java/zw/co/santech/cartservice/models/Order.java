package zw.co.santech.cartservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.santech.cartservice.enums.OrderStatus;
import zw.co.santech.cartservice.enums.PaymentMethod;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_number")
    private Long orderNumber;
    private Long totalCost;
    private String createdDate;
    private String userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Integer totalOrderItems;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "orders_order_number", foreignKey = @ForeignKey(name = "order_item_id_fk"), referencedColumnName = "order_number")
    @JsonManagedReference
    private List<OrderItem> orderItems;
}
