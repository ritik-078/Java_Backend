package com.learnhibernate_jpa.RESTAPIwithMySQL.service;

import com.learnhibernate_jpa.RESTAPIwithMySQL.entity.Product;
import com.learnhibernate_jpa.RESTAPIwithMySQL.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query);
        return products;
    }

    @Override
    public Product createProducts(Product product) {

        return productRepository.save(product);
    }
}
