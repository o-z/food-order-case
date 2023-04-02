package com.craftbase.orderapi.adapters.payment.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "craft-gate.api")
public class CraftGateProperties {
    private String apiKey;
    private String secretKey;
    private String baseUrl;
}
