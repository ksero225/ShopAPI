package com.ShopAPI_GROUP.ShopApi;

import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductCategoryDto;
import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductDto;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;

public class TestDataUtil {

    public static ProductEntity createTestProductA(final ProductCategoryEntity productCategory) {
        return new ProductEntity(1L, "Mouse", 11.30, productCategory);
    }

    public static ProductDto createTestProductDtoA(final ProductCategoryDto productCategory) {
        return new ProductDto(1L, "Mouse", 11.30, productCategory);
    }

    public static ProductEntity createTestProductB(final ProductCategoryEntity productCategory) {
        return new ProductEntity(2L, "T-Shirt", 12.30, productCategory);
    }

    public static ProductEntity createTestProductC(final ProductCategoryEntity productCategory) {
        return new ProductEntity(3L, "Desk", 13.30, productCategory);
    }

    public static ProductCategoryEntity createTestProductCategoryA() {
        return new ProductCategoryEntity("Electronics");
    }


    public static ProductCategoryDto createTestProductCategoryDtoA() {
        return new ProductCategoryDto("Electronics");
    }

    public static ProductCategoryEntity createTestProductCategoryB() {
        return new ProductCategoryEntity("Clothes");
    }

    public static ProductCategoryEntity createTestProductCategoryC() {
        return new ProductCategoryEntity("Furniture");
    }


}
