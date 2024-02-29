package com.ShopAPI_GROUP.ShopApi.services.impl;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import com.ShopAPI_GROUP.ShopApi.repositories.ProductRepository;
import com.ShopAPI_GROUP.ShopApi.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> findAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductEntity> findOne(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public ProductEntity partialUpdate(Long id, ProductEntity productEntity) {
        productEntity.setId(id);

        return productRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(productEntity.getProductName()).ifPresent(existingAuthor::setProductName);
            Optional.ofNullable(productEntity.getProductPrice()).ifPresent(existingAuthor::setProductPrice);

            return productRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Product does not exists"));
    }
}
