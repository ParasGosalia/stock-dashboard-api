package com.sample.payconiq.stocks.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JWTPropertiesConfiguration {

    private String tokenValidity;
    private String secretKey;
}
