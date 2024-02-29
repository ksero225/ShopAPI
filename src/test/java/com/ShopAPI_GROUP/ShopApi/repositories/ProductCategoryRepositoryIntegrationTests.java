package com.ShopAPI_GROUP.ShopApi.repositories;

import com.ShopAPI_GROUP.ShopApi.TestDataUtil;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductCategoryRepositoryIntegrationTests {

    private final ProductCategoryRepository underTest;

    @Autowired
    public ProductCategoryRepositoryIntegrationTests(ProductCategoryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatProductCategoryCanBeCreatedAndRecalled() {
        ProductCategoryEntity productCategory = TestDataUtil.createTestProductCategoryA();

        underTest.save(productCategory);
        Optional<ProductCategoryEntity> result = underTest.findById(productCategory.getCategoryName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(productCategory);
    }

    @Test
    public void testThatMultipleProductCategoryCanBeCreatedAndRecalled() {
        ProductCategoryEntity productCategoryA = TestDataUtil.createTestProductCategoryA();
        underTest.save(productCategoryA);

        ProductCategoryEntity productCategoryB = TestDataUtil.createTestProductCategoryB();
        underTest.save(productCategoryB);

        ProductCategoryEntity productCategoryC = TestDataUtil.createTestProductCategoryC();
        underTest.save(productCategoryC);

        Iterable<ProductCategoryEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactlyInAnyOrder(productCategoryA, productCategoryB, productCategoryC);
    }

    @Test
    public void testThatProductCategoryCanBeUpdated() {
        ProductCategoryEntity productCategoryA = TestDataUtil.createTestProductCategoryA();
        underTest.save(productCategoryA);

        productCategoryA.setCategoryName("UPDATED");
        underTest.save(productCategoryA);

        Optional<ProductCategoryEntity> result = underTest.findById(productCategoryA.getCategoryName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(productCategoryA);
    }

    @Test
    public void testThatProductCategoryCanBeDeleted() {
        ProductCategoryEntity productCategoryA = TestDataUtil.createTestProductCategoryA();
        underTest.save(productCategoryA);

        underTest.deleteById(productCategoryA.getCategoryName());

        Optional<ProductCategoryEntity> result = underTest.findById(productCategoryA.getCategoryName());
        assertThat(result).isEmpty();
    }

}
