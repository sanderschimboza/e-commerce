package zw.co.santech.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private Long additionalPrice;
    private Integer quantityAvailable;
    private Long productCategoryId;
    private String productImageUrlPath;
    private String productDetails1;
    private String productDetails2;
    private String productDetails3;
}
