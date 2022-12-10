package com.sivalabs.bookstore.cart.api;

import com.sivalabs.bookstore.cart.domain.Cart;
import com.sivalabs.bookstore.cart.domain.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(
            @RequestParam(name = "cartId", required = false) String cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public Cart addToCart(
            @RequestParam(name = "cartId", required = false) String cartId,
            @RequestBody @Valid CartItemRequestDTO cartItemRequest) {
        return cartService.addToCart(cartId, cartItemRequest);
    }

    @PutMapping
    public Cart updateCartItemQuantity(
            @RequestParam(name = "cartId") String cartId,
            @RequestBody @Valid CartItemRequestDTO cartItemRequest) {
        return cartService.updateCartItemQuantity(cartId, cartItemRequest);
    }

    @DeleteMapping(value = "/items/{code}")
    public Cart removeCartItem(
            @RequestParam(name = "cartId") String cartId, @PathVariable("code") String code) {
        return cartService.removeCartItem(cartId, code);
    }

    @DeleteMapping
    public void removeCart(@RequestParam(name = "cartId") String cartId) {
        cartService.removeCart(cartId);
    }
}
