package com.scaler.productservicejan31capstone.services;

import com.scaler.productservicejan31capstone.models.Product;

public interface ProductAIService
{
    Product createProductWithAIDescription(String name, double price,
                                           String imageUrl, String category);
}
