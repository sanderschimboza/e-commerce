package zw.co.santech.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.santech.gatewayservice.security.AuthenticationFilter;
import zw.co.santech.gatewayservice.utils.Constants;

@Configuration
@RequiredArgsConstructor
public class SpringCloudConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/authenticate/**")
                        .filters(f -> f
                                .filter(authenticationFilter)
//                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
//                                        .setFallbackUri("forward:/" + Constants.FALLBACK_AUTH_URI)
//                                )
                        )
                        .uri(Constants.LB_AUTHENTICATION_SERVICE)
                )

                .route(r -> r.path("/users/**")
                        .filters(f -> f
                                .filter(authenticationFilter)
//                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
//                                        .setFallbackUri("forward:/" + Constants.FALLBACK_AUTH_URI)
//                                )
                        )
                        .uri(Constants.LB_AUTHENTICATION_SERVICE)
                )

                .route(r -> r.path("/product/**")
                        .filters(f -> f
                                .filter(authenticationFilter)
//                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
//                                        .setFallbackUri("forward:/" + Constants.FALLBACK_PRODUCT_URI)
//                                )
                        )
                        .uri(Constants.LB_PRODUCT_SERVICE)
                )

                .route(r -> r.path("/category/**")
                        .filters(f -> f
                                .filter(authenticationFilter)
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_PRODUCT_URI)
                                )
                        )
                        .uri(Constants.LB_PRODUCT_SERVICE)
                )

                .route(r -> r.path("/cart/**")
                        .filters(f -> f
                                .filter(authenticationFilter)
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_CART_URI)
                                )
                        )
                        .uri(Constants.LB_CART_SERVICE)
                )
                .build();
    }
}
