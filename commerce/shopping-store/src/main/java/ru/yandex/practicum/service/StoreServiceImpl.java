package ru.yandex.practicum.service;


import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.store.*;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.StoreRepository;
import ru.yandex.practicum.dto.store.PageResponse;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreServiceImpl implements StoreService {

        private final StoreRepository storeRepository;

        @Override
        public PageResponse getAllByType(ProductCategory category, Pageable customPageable) {
            if (customPageable.getPage() == null || customPageable.getSize() == null) {
                List<Product> products = storeRepository.getAllByProductCategory(category);
                List<ProductDto> productDtos = products.stream().map(Mapper::mapToProductDto).toList();
                return new PageResponse(productDtos, List.of());
            }

            org.springframework.data.domain.Pageable pageable;
            if (customPageable.getSize() == null) {
                pageable = PageRequest.of(customPageable.getPage(), customPageable.getSize());
                List<Product> products = storeRepository.getAllByProductCategory(category, pageable);
                List<ProductDto> productDtos = products.stream().map(Mapper::mapToProductDto).toList();
                return new PageResponse(productDtos, List.of());
            } else {
                String fieldName = customPageable.getSort()[0];
                String sortingDirection = customPageable.getSort()[1];

                Sort sort = Objects.equals(sortingDirection, "ASC") ? Sort.by(Sort.Direction.ASC, fieldName) :
                        Sort.by(Sort.Direction.DESC, fieldName);
                pageable = PageRequest.of(customPageable.getPage(), customPageable.getSize(), sort);

                List<Product> products = storeRepository.getAllByProductCategory(category, pageable);
                List<ProductDto> productDtos = products.stream().map(Mapper::mapToProductDto).toList();
                return new PageResponse(productDtos, List.of(new SortOrder(fieldName, sortingDirection)));
            }
        }

        @Override
        @Transactional
        public ProductDto addProduct(ProductDto productDto) {
            Product product = storeRepository.save(Mapper.mapToProduct(productDto));
            return Mapper.mapToProductDto(product);
        }

        @Override
        @Transactional
        public ProductDto updateProduct(UpdateProductDto productDto) {
            Product product = storeRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(String.format("\"Нет товара c id = %s", productDto)));

            Optional.ofNullable(productDto.getProductName()).filter(x -> !x.isBlank()).ifPresent(product::setProductName);
            Optional.ofNullable(productDto.getDescription()).filter(x -> !x.isBlank()).ifPresent(product::setDescription);
            Optional.ofNullable(productDto.getImageSrc()).filter(x -> !x.isBlank()).ifPresent(product::setImageSrc);
            Optional.ofNullable(productDto.getQuantityState()).ifPresent(product::setQuantityState);
            Optional.ofNullable(productDto.getProductState()).ifPresent(product::setProductState);
            Optional.ofNullable(productDto.getProductCategory()).ifPresent(product::setProductCategory);
            Optional.ofNullable(productDto.getPrice()).ifPresent(product::setPrice);

            return Mapper.mapToProductDto(product);
        }

        @Override
        @Transactional
        public String removeProduct(String productId) {
            String id = productId.replaceAll("\"", "");
            Product product = storeRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Нет товара c id = %s", productId)));

            if (product.getProductState() == ProductState.DEACTIVATE) {
                return "false";
            }
            product.setProductState(ProductState.DEACTIVATE);
            return "true";
        }

        @Override
        @Transactional
        public String setQuantityState(SetProductQuantityStateRequest stateRequest) {
            Product product = storeRepository.findById(stateRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Нет товара c id = %s",
                            stateRequest.getProductId())));

            if (product.getQuantityState() == stateRequest.getQuantityState()) {
                return "false";
            }
            product.setQuantityState(stateRequest.getQuantityState());
            return "true";
        }

        @Override
        public ProductDto getProduct(String productId) {
            Product product = storeRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Нет товара c id = %s", productId)));

            return Mapper.mapToProductDto(product);
        }
    }