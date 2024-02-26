package com.ShopAPI_GROUP.ShopApi.repositories;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryIntegrationTests {

    private ProductRepository underTest;

    @Autowired
    public ProductRepositoryIntegrationTests(ProductRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatProductCanBeCreatedAndRecalled() {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        underTest.save(productEntity);
        Optional<ProductEntity> result = underTest.findById(productEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(productEntity);
    }

    @Test
    public void testThatMultipleProductsCanBerCreatedAndRecalled() {
        ProductCategoryEntity productCategoryA = TestDataUtil.createTestProductCategoryA();
        ProductCategoryEntity productCategoryB = TestDataUtil.createTestProductCategoryB();
        ProductCategoryEntity productCategoryC = TestDataUtil.createTestProductCategoryC();

        ProductEntity productEntityA = TestDataUtil.createTestProductA(productCategoryA);
        underTest.save(productEntityA);

        ProductEntity productEntityB = TestDataUtil.createTestProductB(productCategoryB);
        underTest.save(productEntityB);

        ProductEntity productEntityC = TestDataUtil.createTestProductC(productCategoryC);
        underTest.save(productEntityC);

        Iterable<ProductEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(productEntityA, productEntityB, productEntityC);
    }

    @Test
    public void testThatProductCanBeUpdated(){
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        underTest.save(productEntity);

        productEntity.setProductName("UPDATED");

        underTest.save(productEntity);

        Optional<ProductEntity> result = underTest.findById(productEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(productEntity);
    }

    @Test
    public void testThatProductCanBeDeleted(){
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();
        ProductEntity productEntity = TestDataUtil.createTestProductA(productCategory);

        underTest.save(productEntity);

        underTest.deleteById(productEntity.getId());

        Optional<ProductEntity> result = underTest.findById(productEntity.getId());
        assertThat(result).isEmpty();
    }
}
