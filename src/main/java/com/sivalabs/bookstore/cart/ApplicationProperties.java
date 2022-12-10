package com.sivalabs.bookstore.cart;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(String productServiceUrl) {}
