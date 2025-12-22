package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.entity.ProductQuantity;
import ru.yandex.practicum.entity.ShoppingCart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public static List<ProductQuantity> mapToProductQuantity(Map<String, Long> productList,
                                                             ShoppingCart shoppingCart) {
        return productList.entrySet().stream()
                .map(x -> ProductQuantity.builder()
                        .productId(x.getKey())
                        .quantity(x.getValue())
                        .shoppingCart(shoppingCart)
                        .build())
                .toList();
    }

    public static ShoppingCartDto mapToShoppingCartDto(String shoppingCartId, List<ProductQuantity> productQuantityList) {
        Map<String, Long> products = productQuantityList.stream()
                .collect(Collectors.toMap(ProductQuantity::getProductId, ProductQuantity::getQuantity));
        return new ShoppingCartDto(shoppingCartId, products);
    }
}
