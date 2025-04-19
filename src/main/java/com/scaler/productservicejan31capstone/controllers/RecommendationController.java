package com.scaler.productservicejan31capstone.controllers;

import com.scaler.productservicejan31capstone.dtos.ProductResponseDto;
import com.scaler.productservicejan31capstone.models.Product;
import com.scaler.productservicejan31capstone.services.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationController
{
    RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService)
    {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations/{productId}")
    public List<ProductResponseDto> getRecommendations(@PathVariable("productId") long productId)
    {
        List<Product> products = recommendationService.getHybridRecommendations(productId);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }
}
