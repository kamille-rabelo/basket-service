package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.dto.CheckoutBasketDto;
import dev.java.ecommerce.basketservice.dto.CreateBasketDto;
import dev.java.ecommerce.basketservice.dto.ProductItemDto;
import dev.java.ecommerce.basketservice.dto.UpdateProductsDto;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.entity.Product;
import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.exception.BasketNotFoundException;
import dev.java.ecommerce.basketservice.exception.ClientHasOpenBasketException;
import dev.java.ecommerce.basketservice.mapper.BasketMapper;
import dev.java.ecommerce.basketservice.mapper.ProductMapper;
import dev.java.ecommerce.basketservice.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final BasketMapper basketMapper;

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found with id: " + id));
    }

    @Transactional
    public Basket createBasket(CreateBasketDto createBasketDto) {
        basketRepository.findByClientIdAndStatus(createBasketDto.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new ClientHasOpenBasketException("Client already has an open basket: " + basket.getId());
                });

        var products = fetchAndMapProducts(createBasketDto.products());
        products.stream()
                .filter(p -> p.getQuantity() <= 0)
                .findAny()
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Product of id " + p.getId() + " has invalid quantity: " + p.getQuantity());
                });

        var basket = basketMapper.toEntity(createBasketDto);
        basket.setProducts(products);
        basket.calculateTotalPrice();

        return basketRepository.save(basket);
    }

    @Transactional
    public Basket updateProductsInBasket(String id, UpdateProductsDto updateProductsDto) {
        var products = fetchAndMapProducts(updateProductsDto.products());

        var basket = getBasketById(id);
        if (basket.getStatus() != Status.OPEN) {
            throw new IllegalStateException("Basket is already checked out");
        }

        updateProductsInBasket(products, basket.getProducts());
        basket.calculateTotalPrice();

        return basketRepository.save(basket);
    }

    @Transactional
    public Basket checkoutBasket(String id, CheckoutBasketDto checkoutBasketDto) {
        var basket = getBasketById(id);
        if (basket.getStatus() != Status.OPEN) {
            throw new IllegalStateException("Basket is already checked out");
        }

        basket.setPaymentMethod(checkoutBasketDto.paymentMethod());
        basket.setStatus(Status.CHECKED_OUT);

        return basketRepository.save(basket);
    }

    private List<Product> fetchAndMapProducts(List<ProductItemDto> products) {
        return products.stream()
                .map(productReq -> {
                    var platziProduct = productService.getProductById(productReq.id());
                    return productMapper.toEntity(platziProduct, productReq);
                })
                .toList();
    }

    private void updateProductsInBasket(List<Product> productsToUpdate, List<Product> products) {
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        productsToUpdate.forEach(product -> {
            var existingProduct = productMap.get(product.getId());
            if (existingProduct == null && product.getQuantity() > 0) {
                products.add(product);
            } else if (product.getQuantity() <= 0) {
                products.remove(existingProduct);
            } else {
                existingProduct.setQuantity(product.getQuantity());
            }
        });
    }
}
