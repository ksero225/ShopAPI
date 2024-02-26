package com.ShopAPI_GROUP.ShopApi.repositories;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
