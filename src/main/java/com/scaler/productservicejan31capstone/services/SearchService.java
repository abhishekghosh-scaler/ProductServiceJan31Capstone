package com.scaler.productservicejan31capstone.services;

import com.scaler.productservicejan31capstone.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface SearchService
{
    Page<Product> search(String query, int pageNumber, int pageSize, String sortParam);
}
