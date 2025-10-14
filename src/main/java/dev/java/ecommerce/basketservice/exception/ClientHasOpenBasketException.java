package dev.java.ecommerce.basketservice.exception;

public class ClientHasOpenBasketException extends RuntimeException {
    public ClientHasOpenBasketException(String message) {
        super(message);
    }
}
