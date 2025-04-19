package com.scaler.productservicejan31capstone.services;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.scaler.productservicejan31capstone.models.Product;
import com.scaler.productservicejan31capstone.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductSyncService {

    private final ProductRepository productRepository;
    private final VectorStore vectorStore;

    public ProductSyncService(ProductRepository productRepository,
                              VectorStore vectorStore) {
        this.productRepository = productRepository;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    @Scheduled(fixedRate = 3600000)
    public void syncProductsToRedis() {
        List<Product> products = productRepository.findAll();
        List<Document> documents = new ArrayList<>();

        for (Product product : products)
        {
            if (product.getDescription() != null)
            {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("id", product.getId());
                metadata.put("category", product.getCategory().getName());
                metadata.put("price", product.getPrice());

                // OpenAI API is called internally by vectorStore.add(documents)
                Document doc = new Document(product.getDescription(), metadata);
                documents.add(doc);
            }
        }

        // This line triggers OpenAI embedding API calls for each document
        vectorStore.add(documents);
    }
}