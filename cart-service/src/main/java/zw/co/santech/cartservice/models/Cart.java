package zw.co.santech.cartservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String productId;
    private Long quantity;
    private Long price;
    private Long additionalPrice;
    private Long totalPrice;
    private String dateAdded;
    private String cartDetails1;
    private String cartDetails2;
    private String cartDetails3;
}
