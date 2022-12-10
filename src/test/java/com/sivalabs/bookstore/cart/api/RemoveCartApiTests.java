package com.sivalabs.bookstore.cart.api;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.sivalabs.bookstore.cart.common.AbstractIntegrationTest;
import com.sivalabs.bookstore.cart.domain.Cart;
import com.sivalabs.bookstore.cart.domain.CartItem;
import com.sivalabs.bookstore.cart.domain.CartRepository;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RemoveCartApiTests extends AbstractIntegrationTest {

    @Autowired private CartRepository cartRepository;

    @Test
    void shouldRemoveCart() {
        String cartId = UUID.randomUUID().toString();
        cartRepository.save(
                new Cart(
                        cartId,
                        Set.of(new CartItem("P100", "Product 1", "P100 desc", BigDecimal.TEN, 2))));
        given().when().delete("/api/carts?cartId={cartId}", cartId).then().statusCode(200);
        assertThat(cartRepository.findById(cartId)).isEmpty();
    }
}
