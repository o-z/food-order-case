package com.example.restaurantapi.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Testcontainers
class BaseMySQLContainerTest {


    @Container
    public static MySQLContainer<?> mySqlDB =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("FOOD_ORDER_CASE_RESTAURANT")
                    .withUsername("admin")
                    .withPassword("admin")
                    .withInitScript("import.sql");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlDB::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlDB::getUsername);
        registry.add("spring.datasource.password", mySqlDB::getPassword);
        registry.add("spring.flyway.enabled", () -> false);
    }

    @Autowired
    protected TestRestTemplate testRestTemplate;
}
