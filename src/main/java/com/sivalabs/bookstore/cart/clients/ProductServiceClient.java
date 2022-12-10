package com.sivalabs.bookstore.cart.clients;

import com.sivalabs.bookstore.cart.ApplicationProperties;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductServiceClient {
    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    public Optional<Product> getProductByCode(String productCode) {
        log.info("Fetching product for productCode: {}", productCode);
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            String url = properties.productServiceUrl() + "/api/products/" + productCode;
            ResponseEntity<Product> response =
                    restTemplate.exchange(url, HttpMethod.GET, httpEntity, Product.class);
            return Optional.ofNullable(response.getBody());
        } catch (RuntimeException e) {
            log.error("Error while fetching product for productCode: " + productCode, e);
            return Optional.empty();
        }
    }
}
