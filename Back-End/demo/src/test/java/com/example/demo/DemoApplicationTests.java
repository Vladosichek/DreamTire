package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestPropertySource(properties = {
  "spring.datasource.url=jdbc:h2:mem:testdb",
  "spring.datasource.driverClassName=org.h2.Driver",
  "spring.datasource.username=sa",
  "spring.datasource.password=password",
  "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}

