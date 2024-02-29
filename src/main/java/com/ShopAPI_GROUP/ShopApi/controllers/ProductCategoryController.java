package com.ShopAPI_GROUP.ShopApi.controllers;


import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductCategoryDto;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.mappers.Mapper;
import com.ShopAPI_GROUP.ShopApi.services.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductCategoryController {

    private ProductCategoryService categoryService;

    private Mapper<ProductCategoryEntity, ProductCategoryDto> productCategoryMapper;

    public ProductCategoryController(ProductCategoryService categoryService, Mapper<ProductCategoryEntity, ProductCategoryDto> productCategoryMapper) {
        this.categoryService = categoryService;
        this.productCategoryMapper = productCategoryMapper;
    }

    @PutMapping(path = "/products_categories/{name}")
    public ResponseEntity<ProductCategoryDto> createUpdateProductCategory(
            @PathVariable("name") String productCategoryName,
            @RequestBody ProductCategoryDto productCategoryDto
    ) {
        ProductCategoryEntity productCategoryEntity = productCategoryMapper.mapFrom(productCategoryDto);
        boolean productCategoryExists = categoryService.isExists(productCategoryName);

        ProductCategoryEntity savedProductCategoryEntity = categoryService.save(productCategoryEntity);
        ProductCategoryDto savedProductCategoryDto = productCategoryMapper.mapTo(savedProductCategoryEntity);

        if (productCategoryExists) {
            return new ResponseEntity<>(savedProductCategoryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedProductCategoryDto, HttpStatus.CREATED);
        }

    }

    // Partial update for product category does not make any sense, because its only one field, leaving this method for future.

//    @PatchMapping(path = "/products_categories/{name}")
//    public ResponseEntity<ProductCategoryDto> partialUpdateProductCategory(
//            @PathVariable("name") String productCategoryName,
//            @RequestBody ProductCategoryDto productCategoryDto
//    ) {
//
//        if(!categoryService.isExists(productCategoryName)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        ProductCategoryEntity productCategoryEntity = productCategoryMapper.mapFrom(productCategoryDto);
//        ProductCategoryEntity updatedProductCategoryEntity = categoryService.partialUpdate(productCategoryName, productCategoryEntity);
//
//        return new ResponseEntity<>(
//                productCategoryMapper.mapTo(updatedProductCategoryEntity),
//                HttpStatus.OK
//        );
//    }

    @GetMapping(path = "/products_categories")
    public List<ProductCategoryDto> listProductCategories() {
        List<ProductCategoryEntity> productCategories = categoryService.findAll();
        return productCategories.stream().map(productCategoryMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/products_categories/{name}")
    public ResponseEntity<ProductCategoryDto> getProductCategory(@PathVariable("name") String productName) {
        Optional<ProductCategoryEntity> foundProductCategory = categoryService.findOne(productName);
        return foundProductCategory.map(productCategoryEntity -> {
            ProductCategoryDto productCategoryDto = productCategoryMapper.mapTo(productCategoryEntity);
            return new ResponseEntity<>(productCategoryDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
