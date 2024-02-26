package com.ShopAPI_GROUP.ShopApi.domain.dto;

import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String productName;
    private Double productPrice;
    private ProductCategoryDto productCategory;
}
