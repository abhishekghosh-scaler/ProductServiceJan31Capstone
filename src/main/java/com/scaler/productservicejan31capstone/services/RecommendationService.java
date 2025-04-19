package com.scaler.productservicejan31capstone.services;

import com.scaler.productservicejan31capstone.models.Product;
import com.scaler.productservicejan31capstone.repositories.ProductRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

@Service
public class RecommendationService
{
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RedisVectorStore vectorStore;
    ProductRepository productRepository;

    public RecommendationService(ProductRepository productRepository, RedisVectorStore vectorStore)
    {
        this.productRepository = productRepository;
        this.vectorStore = vectorStore;
    }

    public List<Product> getHybridRecommendations(long productId)
    {
        Product targetProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with Id "+productId+" not found"));

        List<Product> repoResults  = productRepository.findSimilarInCategory(
                targetProduct.getCategory(), targetProduct.getPrice() * 0.8,
                targetProduct.getPrice() * 1.2, productId
        );

        SearchRequest searchRequest = SearchRequest.builder()
                .query(targetProduct.getDescription())
                .topK(5)
                .build();

        List<Document> aiResults = vectorStore.similaritySearch(searchRequest);

        return combineResults(repoResults, aiResults);
    }

    List<Product> combineResults(List<Product> repoResults,
                                 List<Document> aiResults)
    {
        Set<Product> results = new LinkedHashSet<>();
        results.addAll(repoResults);

        for(Document doc : aiResults)
        {
            logger.info("Document metadata: {}", doc.getMetadata());

            Object idObj = doc.getMetadata().get("id");
            if (idObj == null) {
                // Log a warning and skip this document
                logger.warn("Document missing 'id' metadata: {}", doc.getText());
                continue;
            }

            try {
                Long docProductId = Long.parseLong(idObj.toString());
                productRepository.findById(docProductId).ifPresent(results::add);
            } catch (NumberFormatException e) {
                logger.error("Invalid 'id' format in document metadata: {}", idObj);
            }
        }

        return new ArrayList<>(results);
    }
}
