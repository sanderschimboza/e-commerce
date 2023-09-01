package zw.co.santech.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product implements Serializable{
    @Id
    private String productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private Long additionalPrice;
    private Integer quantityAvailable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category productCategory;
    private String productImageUrlPath;
    private String dateAdded;
    private String productDetails1;
    private String productDetails2;
    private String productDetails3;
}
