package ru.yandex.practicum.http;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.store.*;

public interface ShoppingStore {
    @GetMapping("/api/v1/shopping-store")
    PageResponse getAllByType(@RequestParam ProductCategory category,
                              @ModelAttribute Pageable pageable);

    @PutMapping("/api/v1/shopping-store")
    ProductDto addProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@RequestBody @Valid UpdateProductDto productDto);

    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    String removeProduct(@RequestBody @NotBlank String productId);

    @PostMapping("/api/v1/shopping-store/quantityState")
    String setQuantityState(@ModelAttribute @Valid SetProductQuantityStateRequest stateRequest);

    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto getProduct(@PathVariable @NotBlank String productId);
}