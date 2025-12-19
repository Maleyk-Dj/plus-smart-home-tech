package ru.yandex.practicum.contract.shoppingstore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.store.*;

public interface ShoppingStore {
    @GetMapping
    PageResponse getAllByType(@RequestParam @NotNull ProductCategory category,
                         @ModelAttribute Pageable pageable);

    @PutMapping
    ProductDto addProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid UpdateProductDto productDto);

    @PostMapping("/removeProductFromStore")
    String removeProduct(@RequestBody @NotBlank String productId);

    @PostMapping("/quantityState")
    String setQuantityState(@ModelAttribute @Valid SetProductQuantityStateRequest stateRequest);

    @GetMapping("/{productId}")
    ProductDto getProduct(@PathVariable @NotBlank String productId);
}