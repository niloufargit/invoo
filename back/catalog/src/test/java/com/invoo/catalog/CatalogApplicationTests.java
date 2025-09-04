package com.invoo.catalog;

import com.invoo.catalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CatalogApplicationTests {

	@Autowired
	ProductService productService;

	@Test
	void contextLoads() {
	}

}
