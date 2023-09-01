package zw.co.santech.gatewayservice.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import zw.co.santech.gatewayservice.utils.Constants;

import java.util.Objects;

@RefreshScope
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final JwtManager jwtManager;
    private final RouteValidator routeValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {

            if (isAuthMissing(request))
                return onError(exchange, "Token is Missing in request!");

            final String token = extractToken(request);

            if (!(Objects.isNull(token)) && jwtManager.isInvalidToken(token)) {
                populateRequestWithHeader(exchange, token);
            } else {
                return onError(exchange, "Invalid Token detected inside Gateway Service!");
            }

        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error(err);
        return response.setComplete();
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders()
                .containsKey(Constants.AUTHORIZATION_HEADER);
    }

    private String extractToken(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getOrEmpty(Constants.AUTHORIZATION_HEADER).get(0);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constants.BEARER)) {
            return headerAuth.substring(Constants.BEARER.length());
        }
        return null;
    }

    private void populateRequestWithHeader(ServerWebExchange exchange, String token) {
        Claims claims = jwtManager.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("username",
                        (String) claims.get("username"))
                .header("roles",
                        (String) claims.get("roles"))
                .build();
    }
}











