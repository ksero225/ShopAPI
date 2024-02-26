package com.ShopAPI_GROUP.ShopApi.repositories;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategoryEntity, String> {
}
