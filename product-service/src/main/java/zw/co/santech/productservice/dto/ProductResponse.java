package zw.co.santech.productservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.santech.productservice.models.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;
    ProductDto product;
}
