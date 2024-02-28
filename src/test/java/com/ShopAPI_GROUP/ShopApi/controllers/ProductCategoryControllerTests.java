package com.ShopAPI_GROUP.ShopApi.controllers;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.services.ProductCategoryService;
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
    public void testThatCreateProductReturnsHttpStatus201Created() throws Exception {
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

    @Test
    public void testThatListProductCategoryReturnsHttpStatus200OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products_categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListProductCategoryReturnsProductCategory() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        categoryService.save(productCategory);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products_categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].categoryName").value("Electronics")
        );
    }

    @Test
    public void testThatGetProductCategoryReturnsHttpStatus200WhenProductCategoryExist() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        categoryService.save(productCategory);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products_categories/Electronics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatGetProductCategoryReturnsHttpStatus404WhenNoProductCategoryExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products_categories/noexist")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetProductCategoryReturns0WhenProductCategoryExist() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        categoryService.save(productCategory);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products_categories/Electronics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.categoryName").value("Electronics")
        );
    }
}
