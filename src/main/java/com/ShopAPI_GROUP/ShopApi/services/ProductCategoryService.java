package com.ShopAPI_GROUP.ShopApi.services;

import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductCategoryDto;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCategoryService {
    ProductCategoryEntity createProductCategory(String productCategoryName, ProductCategoryEntity productCategoryEntity);

    List<ProductCategoryEntity> findAll();
}
