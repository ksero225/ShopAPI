package com.ShopAPI_GROUP.ShopApi.services.impl;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.repositories.ProductCategoryRepository;
import com.ShopAPI_GROUP.ShopApi.services.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productRepository) {
        this.productCategoryRepository = productRepository;
    }

    @Override
    public ProductCategoryEntity save(String productCategoryName, ProductCategoryEntity productCategoryEntity) {
        productCategoryEntity.setCategoryName(productCategoryName);
        return productCategoryRepository.save(productCategoryEntity);
    }

    @Override
    public ProductCategoryEntity save(ProductCategoryEntity productCategoryEntity) {
        return productCategoryRepository.save(productCategoryEntity);
    }

    @Override
    public List<ProductCategoryEntity> findAll() {
        return StreamSupport.stream(productCategoryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductCategoryEntity> findOne(String name) {
        return productCategoryRepository.findById(name);
    }
}
