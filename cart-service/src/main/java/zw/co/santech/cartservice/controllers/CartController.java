package zw.co.santech.cartservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.santech.cartservice.dto.CartDto;
import zw.co.santech.cartservice.dto.ProductResponse;
import zw.co.santech.cartservice.enums.PaymentMethod;
import zw.co.santech.cartservice.models.Cart;
import zw.co.santech.cartservice.models.Order;
import zw.co.santech.cartservice.services.CartService;
import zw.co.santech.cartservice.services.PermissionsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    private final PermissionsService permissionsService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto, @RequestHeader String username) throws IOException {
        permissionsService.validateUser(username);
        String productId = cartService.addToCart(cartDto, username);
        return new ResponseEntity<>("Successfully added product => "
                + productId + " to cart", HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable("cartId") Long cartId, @RequestHeader String username) {
        permissionsService.validateUser(username);
        cartService.removeFromCart(cartId, username);
        return new ResponseEntity<>("Product removed from cart successfully.", HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkOutCart(@RequestHeader String username, @RequestParam PaymentMethod paymentMethod) {
        permissionsService.validateUser(username);
        Long cart = cartService.checkOutCart(username, paymentMethod);
        return new ResponseEntity<>("Order created successfully -> " + cart, HttpStatus.CREATED);
    }

    @GetMapping("/findUserOrder")
    public ResponseEntity<List<Order>> findCustomerOrder(@RequestHeader String username) {
        permissionsService.validateUser(username);
        List<Order> userOrders = cartService.findCustomerOrders(username);
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }

    @PutMapping("/modifyQuantity/{cartId}")
    public ResponseEntity<String> addQuantity(@PathVariable("cartId") Long cartId, @RequestParam Long quantity,
                                              @RequestParam String productId, @RequestHeader String username) {

        permissionsService.validateUser(username);
        cartService.addQuantity(cartId, quantity, productId);
        return new ResponseEntity<>("Quantity added successfully.", HttpStatus.OK);
    }

    @GetMapping("/userCart")
    public ResponseEntity<List<Cart>> findUserCart(@RequestHeader String username) {
        permissionsService.validateUser(username);
        List<Cart> cart = cartService.findUserCart(username);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/checkProduct")
    public ResponseEntity<ProductResponse> checkStatus(@RequestParam String productId, @RequestParam Long quantity) {
        ProductResponse productResponse = cartService.getProductResponse(productId, quantity);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<String> deleteCart(@RequestHeader String username) {
        permissionsService.validateUser(username);
        cartService.deleteCart(username);
        return new ResponseEntity<>("User Cart Deleted successfully -> " + username, HttpStatus.OK);
    }
}
