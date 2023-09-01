package zw.co.santech.productservice.services;

import org.springframework.stereotype.Service;
import zw.co.santech.productservice.dto.ProductDto;
import zw.co.santech.productservice.dto.ProductResponse;
import zw.co.santech.productservice.dto.ProductStatus;
import zw.co.santech.productservice.models.Product;

import java.util.List;
public interface ProductService {
    List<Product> findAllProducts(Integer pageNo, Integer pageSize, String sortBy);

    List<Product> findProductsByCategoryId(Long categoryId, Integer pageNo, Integer pageSize, String sortBy);

    Product findProductById(String productId);

    String addProductToShop(ProductDto productDto, String userId);

    ProductResponse checkIfProductExistsAndQuantityIsAvailable(String productId, Integer quantity);

    String modifyProductDetails(String productId, ProductDto productDto, String userId);

    void deleteProductFromShop(String productId, String userId);

    void ModifyProductQuantity(String productId, int newQuantity, String userId);
}
