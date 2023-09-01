package zw.co.santech.cartservice.remote;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import zw.co.santech.cartservice.exceptions.feign.CustomErrorDecoder;

public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}