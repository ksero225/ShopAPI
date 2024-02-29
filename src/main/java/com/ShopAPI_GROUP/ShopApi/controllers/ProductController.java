package com.ShopAPI_GROUP.ShopApi.controllers;

import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductDto;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import com.ShopAPI_GROUP.ShopApi.mappers.Mapper;
import com.ShopAPI_GROUP.ShopApi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ProductService productService;

    private Mapper<ProductEntity, ProductDto> productMapper;


    public ProductController(ProductService productService, Mapper<ProductEntity, ProductDto> productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedAuthorEntity = productService.save(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/products")
    public List<ProductDto> listProducts() {
        List<ProductEntity> products = productService.findAll();
        return products.stream().map(productMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        Optional<ProductEntity> foundProduct = productService.findOne(productId);
        return foundProduct.map(ProductEntity -> {
            ProductDto productDto = productMapper.mapTo(ProductEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> fullUpdateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDto.setId(id);
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(
                productMapper.mapTo(savedProductEntity),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody ProductDto productDto
    ) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity updatedProduct = productService.partialUpdate(id, productEntity);
        return new ResponseEntity<>(
                productMapper.mapTo(updatedProduct),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
