package com.scaler.productservicejan31capstone.services;

import com.scaler.productservicejan31capstone.dtos.FakeStoreProductDto;
import com.scaler.productservicejan31capstone.dtos.FakeStoreProductRequestDto;
import com.scaler.productservicejan31capstone.dtos.ProductResponseDto;
import com.scaler.productservicejan31capstone.exceptions.ProductNotFoundException;
import com.scaler.productservicejan31capstone.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FakeStoreProductServiceTest
{
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    FakeStoreProductService fakeStoreProductService = new FakeStoreProductService(restTemplate);

    @Test
    public void testGetProductByIdReturnsProduct() throws ProductNotFoundException {
        FakeStoreProductDto dummyResponse = new FakeStoreProductDto();
        dummyResponse.setId(1L);
        dummyResponse.setTitle("title");
        dummyResponse.setDescription("description");
        dummyResponse.setPrice(1.0);
        dummyResponse.setImage("img.url");
        dummyResponse.setCategory("category");

        when(restTemplate.getForObject(
                "https://fakestoreapi.com/products/1",
                FakeStoreProductDto.class)).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.getProductById(1L);

        assertEquals(1L, product.getId());
        assertEquals("title", product.getName());
    }

    @Test
    public void testGetProductByIdWithNullProductThrowingException() throws ProductNotFoundException
    {
        when(restTemplate.getForObject(
                "https://fakestoreapi.com/products/1",
                FakeStoreProductDto.class)).thenReturn(null);

        assertThrows(ProductNotFoundException.class,
                () -> fakeStoreProductService.getProductById(1L));
    }

    @Test
    public void testCreateProductReturnsProductWithId()
    {
        FakeStoreProductDto dummyResponse = new FakeStoreProductDto();
        dummyResponse.setId(1L);
        dummyResponse.setTitle("title");
        dummyResponse.setDescription("description");
        dummyResponse.setPrice(1.0);
        dummyResponse.setImage("img.url");
        dummyResponse.setCategory("category");

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreProductDto.class))).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.createProduct("title", "description", 12.1,
                "img.url", "category");

        assertEquals(1L, product.getId());
        assertEquals("title", product.getName());
    }

    @Test
    public void testCreateProductVerifyAPICalls()
    {
        FakeStoreProductDto dummyResponse = new FakeStoreProductDto();
        dummyResponse.setId(1L);
        dummyResponse.setTitle("title");
        dummyResponse.setDescription("description");
        dummyResponse.setPrice(1.0);
        dummyResponse.setImage("img.url");
        dummyResponse.setCategory("category");

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreProductDto.class))).thenReturn(dummyResponse);

        Product product = fakeStoreProductService.createProduct("title", "description", 12.1,
                "img.url", "category");

        verify(restTemplate, times(1)).postForObject(
                eq("https://fakestoreapi.com/products"),
                any(),
                eq(FakeStoreProductDto.class));
    }

}