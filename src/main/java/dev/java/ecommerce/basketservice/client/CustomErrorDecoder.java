package dev.java.ecommerce.basketservice.client;

import dev.java.ecommerce.basketservice.exception.BadRequestException;
import dev.java.ecommerce.basketservice.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad Request");
            case 404 -> new ProductNotFoundException("Product Not Found");
            default -> new Exception("Error while getting product information from Platzi Store: " + response.reason());
        };
    }
}
