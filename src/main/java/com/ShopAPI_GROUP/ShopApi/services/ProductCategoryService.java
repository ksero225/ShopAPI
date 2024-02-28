package com.ShopAPI_GROUP.ShopApi.services;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {
    ProductCategoryEntity save(String productCategoryName, ProductCategoryEntity productCategoryEntity);
    ProductCategoryEntity save(ProductCategoryEntity productCategoryEntity);

    List<ProductCategoryEntity> findAll();

    Optional<ProductCategoryEntity> findOne(String name);

    boolean isExists(String productCategoryName);
}
