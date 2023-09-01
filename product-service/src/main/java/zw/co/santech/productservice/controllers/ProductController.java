package zw.co.santech.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.santech.productservice.dto.ProductDto;
import zw.co.santech.productservice.dto.ProductResponse;
import zw.co.santech.productservice.enums.EnumRoles;
import zw.co.santech.productservice.models.Product;
import zw.co.santech.productservice.services.PermissionService;
import zw.co.santech.productservice.services.ProductService;
import zw.co.santech.productservice.utils.Constants;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<String> addProductToShop(@RequestBody ProductDto productDto, @RequestHeader String username,
                                                   @RequestHeader String roles) {

        permissionService.validateUser(username, roles);

        String productId = productService.addProductToShop(productDto, username);
        return new ResponseEntity<>("Product added successfully -> " + productId, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Product>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> productList = productService.findAllProducts(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/findByCategory/{categoryId}")
    public ResponseEntity<List<Product>> findByCategory(@PathVariable Long categoryId,
                                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "productId") String sortBy) {

        List<Product> productList = productService.findProductsByCategoryId(categoryId, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/modifyProduct/{productId}")
    public ResponseEntity<String> modifyProduct(@PathVariable String productId, @RequestBody ProductDto productDto,
                                                @RequestHeader String username,
                                                @RequestHeader String roles) {

        permissionService.validateUser(username, roles);
        String product = productService.modifyProductDetails(productId, productDto, username);
        return new ResponseEntity<>("Product added successfully -> " + product, HttpStatus.OK);
    }

    @PutMapping("/modifyQuantity/{productId}")
    public ResponseEntity<String> modifyQuantity(@PathVariable String productId, @RequestParam Integer newQuantity,
                                                 @RequestHeader String username,
                                                 @RequestHeader String roles) {

        permissionService.validateUser(username, roles);
        productService.ModifyProductQuantity(productId, newQuantity, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findById/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/checkProduct/{productId}/{quantity}")
    public ResponseEntity<ProductResponse> checkStatus(@PathVariable String productId, @PathVariable Integer quantity) {

        ProductResponse productResponse = productService
                .checkIfProductExistsAndQuantityIsAvailable(productId, quantity);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId, @RequestHeader String username,
                                                @RequestHeader String roles) {

        permissionService.validateUser(username, roles);
        productService.deleteProductFromShop(productId, username);
        return new ResponseEntity<>("Product Successfully deleted: "
                + productId, HttpStatus.OK);
    }
}
