package zw.co.santech.cartservice.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import zw.co.santech.cartservice.dto.ProductResponse;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignClientConfig.class)
public interface RemoteProductService {
    @GetMapping("/product/checkProduct/{productId}/{quantity}")
    ProductResponse getProductResponse(@PathVariable(value = "productId") String productId,
                                       @PathVariable(value = "quantity") Long quantity);
}
