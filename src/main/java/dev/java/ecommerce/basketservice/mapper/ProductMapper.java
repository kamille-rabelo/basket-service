package dev.java.ecommerce.basketservice.mapper;

import dev.java.ecommerce.basketservice.client.dto.PlatziProductDto;
import dev.java.ecommerce.basketservice.dto.ProductDto;
import dev.java.ecommerce.basketservice.dto.ProductItemDto;
import dev.java.ecommerce.basketservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "platziProduct.id", target = "id")
    @Mapping(source = "platziProduct.title", target = "title")
    @Mapping(source = "platziProduct.price", target = "price")
    @Mapping(source = "productItemDto.quantity", target = "quantity")
    Product toEntity(PlatziProductDto platziProduct, ProductItemDto productItemDto);

    List<ProductDto> toResponse(List<Product> products);
}
