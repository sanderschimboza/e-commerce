package zw.co.santech.gatewayservice.utils;

public class Constants {

    public static final String LB_AUTHENTICATION_SERVICE = "lb://AUTH-SERVICE";
    public static final String LB_PRODUCT_SERVICE = "lb://PRODUCT-SERVICE";
    public static final String LB_CART_SERVICE = "lb://CART-SERVICE";
    public static final String FALLBACK_PRODUCT_URI = "fallback/product";
    public static final String FALLBACK_AUTH_URI = "fallback/auth";
    public static final String FALLBACK_CART_URI = "fallback/cart";
    public static final String CB_IDENTITY = "gatewayCb";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String SERVICE_UNAVAILABLE_MSG = "Service is currently unavailable, Please try again later!";
}
