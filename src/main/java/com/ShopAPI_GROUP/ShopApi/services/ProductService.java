package com.ShopAPI_GROUP.ShopApi.services;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductEntity save(ProductEntity productEntity);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findOne(Long productId);

    boolean isExists(Long id);

    ProductEntity partialUpdate(Long id, ProductEntity productEntity);

    void delete(Long id);
}
