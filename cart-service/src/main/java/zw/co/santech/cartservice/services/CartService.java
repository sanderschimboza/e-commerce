package zw.co.santech.cartservice.services;

import zw.co.santech.cartservice.dto.CartDto;
import zw.co.santech.cartservice.dto.ProductResponse;
import zw.co.santech.cartservice.dto.ProductStatus;
import zw.co.santech.cartservice.enums.PaymentMethod;
import zw.co.santech.cartservice.models.Cart;
import zw.co.santech.cartservice.models.Order;

import java.io.IOException;
import java.util.List;

public interface CartService {

    String addToCart(CartDto cartDto, String username) throws IOException;

    void removeFromCart(Long cartId, String userId);

    Long checkOutCart(String userId, PaymentMethod paymentMethod);

    List<Order> findCustomerOrders(String userId);

    void deleteCart(String userId);

    List<Cart> findUserCart(String userId);

    void addQuantity(Long cartId, Long qty, String productId);

    ProductResponse getProductResponse(String productId, Long quantity);

}
