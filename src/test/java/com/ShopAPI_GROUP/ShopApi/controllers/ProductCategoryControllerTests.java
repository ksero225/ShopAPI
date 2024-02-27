package com.ShopAPI_GROUP.ShopApi.controllers;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.services.ProductCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductCategoryControllerTests {

    private ProductCategoryService categoryService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductCategoryControllerTests(ProductCategoryService categoryService, MockMvc mockMvc) {
        this.categoryService = categoryService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateProductReturnsHttpStatus201Created() throws Exception{
        ProductCategoryEntity productCategoryEntity = TestDataUtil.createTestProductCategoryA();
        String productCategoryEntityJson = objectMapper.writeValueAsString(productCategoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products_categories/" + productCategoryEntity.getCategoryName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCategoryEntityJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.categoryName").value("Electronics")
        );
    }

    @Test
    public void testThatCreatedProductCategorySuccessfullyReturnsSavedProductCategory() throws Exception {
        ProductCategoryEntity productCategoryEntity = TestDataUtil.createTestProductCategoryA();
        String productCategoryEntityJson = objectMapper.writeValueAsString(productCategoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products_categories/" + productCategoryEntity.getCategoryName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCategoryEntityJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.categoryName").value(productCategoryEntity.getCategoryName())
        );
    }
    //test
}
