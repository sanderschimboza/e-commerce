package zw.co.santech.cartservice.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import zw.co.santech.cartservice.dto.CartDto;
import zw.co.santech.cartservice.dto.ProductResponse;
import zw.co.santech.cartservice.dto.ProductStatus;
import zw.co.santech.cartservice.enums.ExceptionMessage;
import zw.co.santech.cartservice.enums.OrderStatus;
import zw.co.santech.cartservice.enums.PaymentMethod;
import zw.co.santech.cartservice.exceptions.CartServiceCustomException;
import zw.co.santech.cartservice.exceptions.ResourceNotFoundCustomException;
import zw.co.santech.cartservice.remote.RemoteProductService;
import zw.co.santech.cartservice.models.Cart;
import zw.co.santech.cartservice.models.Order;
import zw.co.santech.cartservice.models.OrderItem;
import zw.co.santech.cartservice.repositories.CartRepository;
import zw.co.santech.cartservice.repositories.OrderItemRepository;
import zw.co.santech.cartservice.repositories.OrderRepository;
import zw.co.santech.cartservice.services.CartService;
import zw.co.santech.cartservice.utils.DateFormatter;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RemoteProductService loadBalancer;

    @Override
    public String addToCart(CartDto cartDto, String username) {

        log.info("Going to ADD to Cart data -> {}", cartDto.toString());

        ProductResponse productResponse = getProductResponse(cartDto.getProductId(), cartDto.getQuantity());

        Cart cart = Cart.builder()
                .productId(cartDto.getProductId())
                .userId(username)
                .price(productResponse.getProduct().getProductPrice())
                .additionalPrice(productResponse.getProduct().getAdditionalPrice())
                .quantity(cartDto.getQuantity())
                .dateAdded(DateFormatter.formatCurrentDate(new Date()))
                .totalPrice((productResponse.getProduct().getProductPrice()
                        + (Objects.isNull(productResponse.getProduct().getAdditionalPrice()) ? 0L
                        : productResponse.getProduct().getAdditionalPrice()))
                        * cartDto.getQuantity())
                .build();

        Optional<Cart> optionalCart = cartRepository.findCartByUserIdAndProductId(
                cart.getUserId(), cart.getProductId());

        if (optionalCart.isPresent()) {
            log.error("Product is already present in your cart!");
            throw new CartServiceCustomException(
                    "Product is already present in your cart!",
                    ExceptionMessage.PRODUCT_ALREADY_ADDED_IN_CART,
                    HttpStatus.BAD_REQUEST

            );
        }

        cartRepository.save(cart);
        log.info("Successfully ADDED to Cart -> {}", productResponse.getProduct().getProductId());
        return productResponse.getProduct().getProductId();
    }

    @Override
    public void removeFromCart(Long cartId, String userId) {
        log.info("Going to REMOVE cartId -> {}", cartId);
        findCartById(cartId);
        cartRepository.deleteById(cartId);
    }

    @Override
    @Transactional
    public Long checkOutCart(String userId, PaymentMethod paymentMethod) {

        List<Cart> cartList = getUserCheckoutCart(userId);

        checkCartProductsAvailability(cartList);

        Order order = Order.builder()
                .createdDate(DateFormatter.formatCurrentDate(new Date()))
                .userId(userId)
                .paymentMethod(paymentMethod)
                .orderStatus(OrderStatus.CREATED)
                .totalCost(getTotalCartCost(cartList))
                .build();

        order = orderRepository.save(order);

        log.info("Order before order-items => {}", order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart cartItem : cartList) {
            OrderItem orderItem = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .price(cartItem.getPrice())
                    .createdDate(DateFormatter.formatCurrentDate(new Date()))
                    .order(order)
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItem = orderItemRepository.save(orderItem);

            log.info("Creating order-item => {}", orderItem);

            orderItems.add(orderItem);
        }

        log.info("All Order-items for order => " + orderItems);

        order.setOrderItems(orderItems);
        order.setTotalOrderItems(orderItems.size());

        orderRepository.save(order);
        cartRepository.deleteAllByUserId(userId);

        log.info("Order created with order number => " + order.getOrderNumber());

        return order.getOrderNumber();
    }

    @Override
    public List<Order> findCustomerOrders(String userId) {
        return orderRepository.findOrderByUserId(userId);
    }

    private void checkCartProductsAvailability(List<Cart> cartList) {
        for (Cart cart : cartList) {
            getProductResponse(cart.getProductId(), cart.getQuantity());
        }
    }

    private Long getTotalCartCost(List<Cart> cartList) {
        long totalPrice = 0;

        for (Cart cart : cartList) {
            log.info("Price in the cart => {}", cart.getTotalPrice());
            totalPrice += cart.getTotalPrice();
        }
        log.info("Total price inside the cart => {}", totalPrice);
        return totalPrice;
    }

    private List<Cart> getUserCheckoutCart(String user) {
        List<Cart> cartList = findUserCart(user);
        if (cartList.isEmpty()) {
            throw new CartServiceCustomException(
                    "Cart not perform checkout. Please add items to your cart first!",
                    ExceptionMessage.EMPTY_CHECKOUT
            );
        }
        return cartList;
    }

    @Override
    @Transactional
    public void deleteCart(String userId) {
        log.info("Going to DELETE user cart -> {}", userId);
        cartRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<Cart> findUserCart(String userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public void addQuantity(Long cartId, Long qty, String productId) {
        log.info("Going to modify QUANTITY with value -> {} for cartId -> {}", qty, cartId);
        getProductResponse(productId, qty);

        Cart cart = getCartByIdAndProductId(cartId, productId);
        cart.setQuantity(qty);
        cart.setTotalPrice(cart.getQuantity() * (cart.getPrice() + cart.getAdditionalPrice()));

        cartRepository.save(cart);
    }

    @Override
    public ProductResponse getProductResponse(String productId, Long quantity) throws RestClientException {
        if (quantity <= 0)
            throw new CartServiceCustomException(
                    "Come on.. A quantity of " + quantity + " is obviously not valid.",
                    ExceptionMessage.INVALID_QUANTITY,
                    HttpStatus.BAD_REQUEST
            );

        ProductResponse productResponse = loadBalancer.getProductResponse(productId, quantity);
        log.info("Got Product Status -> {}", productResponse);

        if (productResponse.getProductStatus().equals(ProductStatus.PRODUCT_UNAVAILABLE))
            throw new ResourceNotFoundCustomException(
                    "The given product: " + productId + " is not available at the moment!",
                    ExceptionMessage.PRODUCT_UNAVAILABLE
            );

        else if (productResponse.getProductStatus().equals(ProductStatus.QUANTITY_EXCEEDED))
            throw new ResourceNotFoundCustomException(
                    "The given quantity of " + quantity + " exceeds the available quantity of "
                            + productResponse.getProduct().getQuantityAvailable() + " for the product " + productId,
                    ExceptionMessage.PRODUCT_UNAVAILABLE
            );

        else if (productResponse.getProductStatus().equals(ProductStatus.PRODUCT_AVAILABLE))
            return productResponse;

        else
            throw new ResourceNotFoundCustomException(
                    "The given product: " + productId + " is unavailable for an unknown reason! Please try again later",
                    ExceptionMessage.PRODUCT_UNAVAILABLE
            );
    }

    private Cart getCartByIdAndProductId(Long cartId, String productId) {
        return cartRepository.findCartByIdAndProductId(cartId, productId)
                .orElseThrow(() -> new ResourceNotFoundCustomException(
                                "Cart with Cart id " + cartId + " and product Id " + productId + "is not found!",
                                ExceptionMessage.CART_NOT_FOUND
                        )
                );
    }

    private Cart findCartById(Long cartId) {
        return cartRepository.findCartById(cartId)
                .orElseThrow(() -> new ResourceNotFoundCustomException(
                                "Cart with cart id: " + cartId + " is not found",
                                ExceptionMessage.CART_NOT_FOUND
                        )
                );
    }
}






