package com.learnhibernate_jpa.RESTAPIwithMySQL.service;

import com.learnhibernate_jpa.RESTAPIwithMySQL.entity.Product;

import java.net.URI;
import java.util.List;

public interface ProductService {
    List<Product> searchProducts(String query);

    Product createProducts(Product product);
}
