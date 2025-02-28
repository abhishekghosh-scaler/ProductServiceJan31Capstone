package com.scaler.productservicejan31capstone.dtos;

import com.scaler.productservicejan31capstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto
{
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;

    public static ProductResponseDto from(Product product)
    {
        if(product == null)
        {
            return null;
        }

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory().getName());

        return productResponseDto;
    }
}
