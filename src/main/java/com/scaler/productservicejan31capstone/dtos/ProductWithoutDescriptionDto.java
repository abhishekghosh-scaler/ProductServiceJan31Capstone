package com.scaler.productservicejan31capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithoutDescriptionDto
{
    private String name;
    private double price;
    private String category;
    private String imageUrl;
}
