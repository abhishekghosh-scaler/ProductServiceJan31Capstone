package com.scaler.productservicejan31capstone.controllers;

import com.scaler.productservicejan31capstone.commons.ApplicationCommons;
import com.scaler.productservicejan31capstone.dtos.CreateFakeStoreProductDto;
import com.scaler.productservicejan31capstone.dtos.ProductResponseDto;
import com.scaler.productservicejan31capstone.dtos.ProductWithoutDescDto;
import com.scaler.productservicejan31capstone.exceptions.ProductNotFoundException;
import com.scaler.productservicejan31capstone.models.Product;

import com.scaler.productservicejan31capstone.services.ProductAIService;
import com.scaler.productservicejan31capstone.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController
{

    private final ProductAIService productAIService;
    ProductService productService;
    ApplicationCommons applicationCommons;

    public ProductController(@Qualifier("fakeStoreProductService")
                             ProductService productService,
                             ApplicationCommons applicationCommons, ProductAIService productAIService)
    {
        this.productService = productService;
        this.applicationCommons = applicationCommons;
        this.productAIService = productAIService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(
            @PathVariable("id") long id,
            @RequestHeader("Authorization") String token) throws ProductNotFoundException
    {
        //validating the token
        applicationCommons.validateToken(token);

        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        return productResponseDto;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

//        List<ProductResponseDto> productResponseDtos =
//                products.stream().map(ProductResponseDto::from)
//                        .collect(Collectors.toList());

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody
                                                CreateFakeStoreProductDto createFakeStoreProductDto)
    {
        Product product = productService.createProduct(
                createFakeStoreProductDto.getName(),
                createFakeStoreProductDto.getDescription(),
                createFakeStoreProductDto.getPrice(),
                createFakeStoreProductDto.getImageUrl(),
                createFakeStoreProductDto.getCategory()
        );

        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        return productResponseDto;
    }

    @PostMapping("/products-without-description")
    public ProductResponseDto createProductWithAIDescription(
            @RequestBody ProductWithoutDescDto productWithoutDescDto)
    {
        Product product = productAIService.createProductWithAIDescription(
                productWithoutDescDto.getName(),
                productWithoutDescDto.getPrice(),
                productWithoutDescDto.getImageUrl(),
                productWithoutDescDto.getCategory()
                );

        return ProductResponseDto.from(product);
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ErrorDto handleNullPointerExceptions()
//    {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setStatus("Failure");
//        errorDto.setMessage("NullPointer exception occurred");
//
//        return errorDto;
//    }

}
