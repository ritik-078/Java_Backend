package com.learnhibernate_jpa.RESTAPIwithMySQL;

import com.learnhibernate_jpa.RESTAPIwithMySQL.entity.Product;
import com.learnhibernate_jpa.RESTAPIwithMySQL.repository.ProductRepository;
import com.learnhibernate_jpa.RESTAPIwithMySQL.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@Test
	void saveMethod() {
		Product product = new Product();
		product.setName("Sunglasses 2");
		product.setSku("EyeWear 2");
		product.setDescription("Green and Blue Coating");
		product.setActive(false);
		product.setImageUrl("sunglasses2.png");
		Product savedProduct =  productService.createProducts(product);
		System.out.println(savedProduct.toString());
	}

	@Test
	void contextLoads() {

	}

}
