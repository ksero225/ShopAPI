package com.ShopAPI_GROUP.ShopApi.controllers;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductCategoryDto;
import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductDto;
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
    public void testThatCreateProductSuccessfullyReturnsHttp201Created() throws Exception {
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

    @Test
    public void testThatListProductsReturnsHttpStatus200OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListProductsReturnsProduct() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);
        productEntity.setId(null);

        productService.save(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].productName").value("Mouse")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].productPrice").value(11.30)
        );
    }

    @Test
    public void testThatGetProductReturnsHttpStatus200WhenProductExist() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        productService.save(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetProductReturnsHttpStatus404WhenNoProductExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetProductReturns0WhenProductExist() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        productService.save(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productName").value("Mouse")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productPrice").value(11.30)
        );
    }

    @Test
    public void testThatFullUpdateProductReturnsHttpStatus200WhenNoProductExist() throws Exception {
        ProductCategoryDto productCategoryDto = TestDataUtil.createTestProductCategoryDtoA();
        ProductDto productDto = TestDataUtil.createTestProductDtoA(productCategoryDto);

        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateProductReturnsHttpStatus200WhenProductExist() throws Exception {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        ProductEntity savedProductEntity = productService.save(productEntity);

        ProductCategoryDto productCategoryDto = TestDataUtil.createTestProductCategoryDtoA();
        ProductDto productDto = TestDataUtil.createTestProductDtoA(productCategoryDto);

        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + savedProductEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateProductUpdatesExistingProduct() throws Exception{
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        ProductEntity savedProductEntity = productService.save(productEntity);

        ProductCategoryEntity savedProductCategory = TestDataUtil.createTestProductCategoryB();
        ProductEntity productDto = TestDataUtil.createTestProductB(savedProductCategory);

        productDto.setId(savedProductEntity.getId());

        String productDtoUpdateJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + savedProductEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedProductEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productName").value(productDto.getProductName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.productPrice").value(productDto.getProductPrice())
        );

    }
}