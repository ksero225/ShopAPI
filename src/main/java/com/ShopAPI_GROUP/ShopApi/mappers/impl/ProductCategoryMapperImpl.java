package com.ShopAPI_GROUP.ShopApi.mappers.impl;

import com.ShopAPI_GROUP.ShopApi.domain.dto.ProductCategoryDto;
import com.ShopAPI_GROUP.ShopApi.domain.entities.ProductCategoryEntity;
import com.ShopAPI_GROUP.ShopApi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapperImpl implements Mapper<ProductCategoryEntity, ProductCategoryDto> {

    private final ModelMapper modelMapper;

    public ProductCategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductCategoryDto mapTo(ProductCategoryEntity productCategoryEntity) {
        return modelMapper.map(productCategoryEntity, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryEntity mapFrom(ProductCategoryDto productCategoryDto) {
        return modelMapper.map(productCategoryDto, ProductCategoryEntity.class);
    }


}
