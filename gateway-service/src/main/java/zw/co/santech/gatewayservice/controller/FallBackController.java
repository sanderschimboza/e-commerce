package zw.co.santech.gatewayservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.santech.gatewayservice.utils.Constants;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @RequestMapping("/product")
    public ResponseEntity<String> ProductFallBack() {
        return new ResponseEntity<>("Product "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/cart")
    public ResponseEntity<String> CartFallBack() {
        return new ResponseEntity<>("Cart "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/auth")
    public ResponseEntity<String> AuthFallBack() {
        return new ResponseEntity<>("Authentication "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }
}
