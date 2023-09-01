package zw.co.santech.cartservice.enums;

import lombok.Getter;

public enum OrderStatus {
    CREATED("Order has been created. Awaiting delivery"),
    IN_TRANSIT("Order has been confirmed and is on it's way"),
    FAILED("Order has been cancelled or rejected"),
    DELIVERED("Order has been delivered");

    @Getter
    final
    String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
