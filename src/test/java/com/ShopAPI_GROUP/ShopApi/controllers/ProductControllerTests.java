package com.ShopAPI_GROUP.ShopApi.controllers;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import com.ShopAPI_GROUP.ShopApi.services.ProductService;
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
class ProductControllerTests {

    private ProductService productService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductControllerTests(ProductService productService, MockMvc mockMvc) {
        this.productService = productService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateProductSuccessfullyReturnsHttp201Created() throws Exception{
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);
        productEntity.setId(null);
        String productJson = objectMapper.writeValueAsString(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatedProductSuccessfullyReturnsSavedProduct() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);
        productEntity.setId(null);
        String productJson = objectMapper.writeValueAsString(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productName").value("Mouse")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productPrice").value(11.30)
        );

    }
}