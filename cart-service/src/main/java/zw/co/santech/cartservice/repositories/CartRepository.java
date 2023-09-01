package zw.co.santech.cartservice.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import zw.co.santech.cartservice.models.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserIdAndProductId(String userId, String productId);

    Optional<Cart> findCartById(Long cartId);

    Optional<Cart> findCartByIdAndProductId(Long cartId, String productId);

    @Modifying
    void deleteAllByUserId(String userId);

    List<Cart> findCartByUserId(String userId);
}
