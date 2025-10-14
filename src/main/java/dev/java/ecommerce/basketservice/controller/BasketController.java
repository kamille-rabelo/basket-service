package dev.java.ecommerce.basketservice.controller;

import dev.java.ecommerce.basketservice.dto.BasketDto;
import dev.java.ecommerce.basketservice.dto.CheckoutBasketDto;
import dev.java.ecommerce.basketservice.dto.CreateBasketDto;
import dev.java.ecommerce.basketservice.dto.UpdateProductsDto;
import dev.java.ecommerce.basketservice.mapper.BasketMapper;
import dev.java.ecommerce.basketservice.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final BasketMapper basketMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BasketDto> getBasketById(@PathVariable String id) {
        var basket = basketService.getBasketById(id);
        return ResponseEntity.ok(
            basketMapper.toResponse(basket)
        );
    }

    @PostMapping
    public ResponseEntity<BasketDto> createBasket(@RequestBody CreateBasketDto createBasketDto) {
        var basket = basketService.createBasket(createBasketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                basketMapper.toResponse(basket)
        );
    }

    @PatchMapping("/{id}/products")
    public ResponseEntity<BasketDto> updateProductsInBasket(@PathVariable String id, @RequestBody UpdateProductsDto updateProductsDto) {
        var basket = basketService.updateProductsInBasket(id, updateProductsDto);
        return ResponseEntity.ok(
                basketMapper.toResponse(basket)
        );
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<BasketDto> checkoutBasket(@PathVariable String id, @RequestBody CheckoutBasketDto checkoutBasketDto) {
        var basket = basketService.checkoutBasket(id, checkoutBasketDto);
        return ResponseEntity.ok(
                basketMapper.toResponse(basket)
        );
    }
}
