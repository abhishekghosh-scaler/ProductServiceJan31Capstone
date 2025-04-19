package com.scaler.productservicejan31capstone.services;

import com.scaler.productservicejan31capstone.exceptions.ProductNotFoundException;
import com.scaler.productservicejan31capstone.models.Category;
import com.scaler.productservicejan31capstone.models.Product;
import com.scaler.productservicejan31capstone.repositories.CategoryRepository;
import com.scaler.productservicejan31capstone.repositories.ProductRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDBService")
public class ProductDBService implements ProductService, ProductAIService
{

    private final ChatClient chatClient;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductDBService(ProductRepository productRepository,
                            CategoryRepository categoryRepository, ChatClient chatClient)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.chatClient = chatClient;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException
    {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty())
        {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price,
                                 String imageUrl, String category)
    {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDB(category);

        product.setCategory(categoryObj);
        return productRepository.save(product);
    }

    private Category getCategoryFromDB(String name)
    {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if(optionalCategory.isPresent())
        {
            return optionalCategory.get();
        }

        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    @Override
    public Product createProductWithAIDescription(String name, double price, String imageUrl, String category)
    {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDB(category);
        product.setCategory(categoryObj);

        String description = getDescriptionFromAI(product);
        product.setDescription(description);

        return productRepository.save(product);
    }

    private String getDescriptionFromAI(Product product)
    {
        String prompt = String.format(
                "Generate a 150-word professional marketing description for a %s product named '%s'. " +
                        "Key features: Priced at $%.2f, Category: %s. " +
                        "Focus on benefits and unique selling points. Avoid technical jargon. Use markdown formatting.",
                product.getCategory().getName().toLowerCase(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getName()
        );

        return chatClient.prompt().user(prompt).call().content();
    }
}
