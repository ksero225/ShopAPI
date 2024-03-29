package com.ShopAPI_GROUP.ShopApi.services.impl;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import com.ShopAPI_GROUP.ShopApi.repositories.ProductCategoryRepository;
import com.ShopAPI_GROUP.ShopApi.repositories.ProductRepository;
import com.ShopAPI_GROUP.ShopApi.services.ProductService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        String categoryName = productEntity.getProductCategoryEntity().getCategoryName();
        Optional<ProductCategoryEntity> existingCategory = categoryRepository.findById(categoryName);

        if (existingCategory.isPresent()) {
            productEntity.setProductCategoryEntity(existingCategory.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found"
            );
        }
        System.out.println("---------------" + categoryRepository.findById(categoryName));
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

        return productRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(productEntity.getProductName()).ifPresent(existingProduct::setProductName);
            Optional.ofNullable(productEntity.getProductPrice()).ifPresent(existingProduct::setProductPrice);

            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product does not exists"));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
