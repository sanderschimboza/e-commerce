package zw.co.santech.productservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.santech.productservice.dto.ProductDto;
import zw.co.santech.productservice.dto.ProductResponse;
import zw.co.santech.productservice.dto.ProductStatus;
import zw.co.santech.productservice.exceptions.ProductServiceCustomException;
import zw.co.santech.productservice.models.Category;
import zw.co.santech.productservice.models.Product;
import zw.co.santech.productservice.repositories.CategoryRepository;
import zw.co.santech.productservice.repositories.ProductRepository;
import zw.co.santech.productservice.services.ProductService;
import zw.co.santech.productservice.utils.Constants;
import zw.co.santech.productservice.utils.DateFormatter;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> findAllProducts(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Going to fetch all products with params: pageSize : " + pageSize + " pageNo: " + pageNo);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Product> findProductsByCategoryId(Long categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Category category = findCategoryById(categoryId);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findProductByProductCategory(category, paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                                "Product with given Id: " + productId
                                        + " was not found!",
                                Constants.CODE_PRODUCT_NOT_FOUND
                        )
                );
    }

    @Override
    public String addProductToShop(ProductDto productDto, String userId) {
        Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());

        if (optionalProduct.isPresent()) {
            throw new ProductServiceCustomException(
                    "Product with Id: " + productDto.getProductId()
                            + " is already defined!",
                    Constants.CODE_PRODUCT_ALREADY_ADDED
            );
        }
        Product product = dressUpProductData(productDto);
        product.setDateAdded(DateFormatter
                .formatCurrentDate(new Date()));

        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse checkIfProductExistsAndQuantityIsAvailable(String productId, Integer quantity) {
        log.info("Going to check the existence of Product Id: {} as well as it's quantity: {}", productId, quantity);
        Optional<Product> product = this.productRepository.findById(productId);

        if (product.isEmpty()) {
            return ProductResponse.builder()
                    .productStatus(ProductStatus.PRODUCT_UNAVAILABLE)
                    .build();

        } else if (product.get().getQuantityAvailable() >= quantity) {

            return ProductResponse.builder()
                    .productStatus(ProductStatus.PRODUCT_AVAILABLE)
                    .product(ProductDto.builder()
                            .productId(product.get().getProductId())
                            .productName(product.get().getProductName())
                            .productPrice(product.get().getProductPrice())
                            .additionalPrice((Objects.isNull(product.get().getAdditionalPrice()) ? 0L
                                    : product.get().getAdditionalPrice()))
                            .quantityAvailable(product.get().getQuantityAvailable())
                            .productDescription(product.get().getProductDescription())
                            .productImageUrlPath(product.get().getProductImageUrlPath())
                            .productCategoryId(product.get().getProductCategory().getCategoryId())
                            .productDetails1(product.get().getProductDetails1())
                            .productDetails2(product.get().getProductDetails2())
                            .productDetails3(product.get().getProductDetails3())
                            .build())
                    .build();

        } else if (product.get().getQuantityAvailable() <= 0) {
            return ProductResponse.builder()
                    .productStatus(ProductStatus.PRODUCT_UNAVAILABLE)
                    .build();
        }
        return ProductResponse.builder()
                .productStatus(ProductStatus.QUANTITY_EXCEEDED)
                .product(ProductDto.builder()
                        .quantityAvailable(product.get().getQuantityAvailable())
                        .build())
                .build();
    }

    @Override
    public String modifyProductDetails(String productId, ProductDto productDto, String userId) {
        Product optionalProduct = findProductById(productId);
        Product product = dressUpProductData(productDto);
        product.setDateAdded(optionalProduct.getDateAdded());

        productRepository.save(product);
        return optionalProduct.getProductId();
    }

    @Override
    public void deleteProductFromShop(String productId, String userId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void ModifyProductQuantity(String productId, int newQuantity, String userId) {
        if (newQuantity < 0) {
            throw new ProductServiceCustomException(
                    "Come on! Quantity of less than zero is unacceptable!",
                    Constants.CODE_INVALID_QUANTITY
            );
        }
        Product optionalProduct = findProductById(productId);
        optionalProduct.setQuantityAvailable(newQuantity);
        productRepository.save(optionalProduct);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductServiceCustomException(
                                "Given Category Id: " + categoryId + " was not found!",
                                Constants.CODE_CATEGORY_NOT_FOUND
                        )
                );
    }

    private Product dressUpProductData(ProductDto productDto) {
        Category category = findCategoryById(productDto.getProductCategoryId());
        return Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .productDescription(productDto.getProductDescription())
                .productImageUrlPath(productDto.getProductImageUrlPath())
                .quantityAvailable(productDto.getQuantityAvailable())
                .productCategory(Category.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .categoryDescription(category.getCategoryDescription())
                        .build())
                .productDetails1(productDto.getProductDetails1())
                .productDetails2(productDto.getProductDetails2())
                .productDetails3(productDto.getProductDetails3())
                .build();
    }
}
