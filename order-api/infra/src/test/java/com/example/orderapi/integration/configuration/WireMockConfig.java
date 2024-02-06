package com.example.orderapi.integration.configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.example.orderapi.adapters.product.model.dto.ProductAdapterDto;
import com.example.orderapi.adapters.product.model.dto.ProductOptionAdapterDto;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.product.model.enums.ProductStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(locations = "classpath:application-test.yaml")
public class WireMockConfig {

    @Value("${microservice.product-api}")
    private String externalServiceUrl;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() throws JsonProcessingException {

        WireMockServer wireMockServer = new WireMockServer(8080);
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/v1/products/66d551b7-2612-4bda-96e3-7cd082937465"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(getProductAdapterDto()))));
        wireMockServer.stubFor(get(urlEqualTo("/v1/products?uuids=66d551b7-2612-4bda-96e3-7cd082937465"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(new ObjectMapper().registerModule(new JavaTimeModule())
                                .writeValueAsString(Map.of(UUID.fromString("6222199b-31d1-4c36-94fb-5826d57def6b"),
                                        getProductAdapterDto())))));
        WireMock.configureFor(externalServiceUrl, 8080);
        return wireMockServer;
    }

    private ProductAdapterDto getProductAdapterDto() {
        return ProductAdapterDto.builder()
                .id(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"))
                .name("Product")
                .desc("Product")
                .imageUrl("url")
                .categoryId(UUID.fromString("9f5f6a8d-a44d-4635-af06-7278a66b25bc"))
                .restaurantFranchisingId(UUID.fromString("b2479521-8140-48a5-8092-b9df5a0aa47f"))
                .price(BigDecimal.valueOf(100))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .createDate(LocalDateTime.parse("2023-03-28T23:38:18"))
                .updateDate(LocalDateTime.parse("2023-03-28T23:38:18"))
                .productOptionMap(Map.of(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"),
                        ProductOptionAdapterDto.builder()
                                .id(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"))
                                .name("extra öz")
                                .desc("extra öz")
                                .price(BigDecimal.valueOf(10))
                                .priceType(PriceType.TRY)
                                .restaurantFranchisingId(UUID.fromString("a3bb4e04-241e-46c9-9734-36b703e3487f"))
                                .status(ProductStatus.ACTIVE)
                                .createDate(LocalDateTime.parse("2023-03-28T23:38:18"))
                                .updateDate(LocalDateTime.parse("2023-03-28T23:38:18"))
                                .build()))
                .build();
    }

}