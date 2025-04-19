// RedisVectorConfig.java
package com.scaler.productservicejan31capstone.configs;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;

@Configuration
public class RedisVectorConfig {

    @Bean
    public JedisPooled jedisPooled() {
        return new JedisPooled("localhost", 6379);
    }

    @Bean
    public VectorStore redisVectorStore(JedisPooled jedisPooled,
                                        EmbeddingModel embeddingModel) {
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName("product-recommendations")
                .prefix("product:embedding:")
                .metadataFields(
                        RedisVectorStore.MetadataField.tag("category"),
                        RedisVectorStore.MetadataField.numeric("price"),
                        RedisVectorStore.MetadataField.numeric("id")
                )
                .embeddingFieldName("embedding")  // Correct method name
                .build();
    }
}
