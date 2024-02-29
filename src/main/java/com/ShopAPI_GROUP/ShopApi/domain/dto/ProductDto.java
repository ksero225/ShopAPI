package com.ShopAPI_GROUP.ShopApi.domain.dto;

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
