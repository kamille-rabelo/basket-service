package dev.java.ecommerce.basketservice.mapper;

import dev.java.ecommerce.basketservice.dto.BasketDto;
import dev.java.ecommerce.basketservice.dto.CreateBasketDto;
import dev.java.ecommerce.basketservice.entity.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface BasketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    Basket toEntity(CreateBasketDto createBasketDto);

    @Mapping(source = "paymentMethod", target = "paymentMethod")
    BasketDto toResponse(Basket basket);
}
