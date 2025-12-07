package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.dto.store.*;
import ru.yandex.practicum.service.StoreService;


@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public PageResponse getAllByType(@RequestParam ProductCategory category,
                                @ModelAttribute Pageable pageable) {
        log.info("Запущен метод getAllByType(ProductCategory category = {}, String pageable = {})", category, pageable);
        return storeService.getAllByType(category, pageable);
    }

    @PutMapping
    public ProductDto addProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Запущен метод addProduct(ProductDto productDto = {})", productDto);
        return storeService.addProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid UpdateProductDto productDto) {
        log.info("Запущен метод updateProduct(UpdateProductDto productDto = {})", productDto);
        return storeService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public String removeProduct(@RequestBody @NotBlank String productId) {
        log.info("Запущен метод removeProduct(String productId = {})", productId);
        return storeService.removeProduct(productId);
    }

    @PostMapping("/quantityState")
    public String setQuantityState(@ModelAttribute @Valid SetProductQuantityStateRequest stateRequest) {
        log.info("Запущен метод setQuantityState(SetProductQuantityStateRequest stateRequest = {})", stateRequest);
        return storeService.setQuantityState(stateRequest);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable @NotBlank String productId) {
        log.info("Запущен метод getProduct(String productId)  = {})", productId);
        return storeService.getProduct(productId);
    }
}
