package com.sivalabs.bookstore.cart.domain;

import com.sivalabs.bookstore.cart.api.CartItemRequestDTO;
import com.sivalabs.bookstore.cart.clients.Product;
import com.sivalabs.bookstore.cart.clients.ProductNotFoundException;
import com.sivalabs.bookstore.cart.clients.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    public Cart getCart(String cartId) {
        if (!StringUtils.hasText(cartId)) {
            return cartRepository.save(Cart.withNewId());
        }
        return cartRepository
                .findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    public Cart addToCart(String cartId, CartItemRequestDTO cartItemRequest) {
        Cart cart;
        if (!StringUtils.hasText(cartId)) {
            cart = Cart.withNewId();
        } else {
            cart =
                    cartRepository
                            .findById(cartId)
                            .orElseThrow(() -> new CartNotFoundException(cartId));
        }
        log.info("Add code: {} to cart", cartItemRequest.getCode());
        Product product =
                productServiceClient
                        .getProductByCode(cartItemRequest.getCode())
                        .orElseThrow(() -> new ProductNotFoundException(cartItemRequest.getCode()));
        CartItem cartItem =
                new CartItem(
                        product.getCode(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        cartItemRequest.getQuantity() > 0 ? cartItemRequest.getQuantity() : 1);
        cart.addItem(cartItem);
        return cartRepository.save(cart);
    }

    public Cart updateCartItemQuantity(String cartId, CartItemRequestDTO cartItemRequest) {
        Cart cart =
                cartRepository
                        .findById(cartId)
                        .orElseThrow(() -> new CartNotFoundException(cartId));
        log.info(
                "Update quantity: {} for code:{} quantity in cart: {}",
                cartItemRequest.getQuantity(),
                cartItemRequest.getCode(),
                cartId);

        if (cartItemRequest.getQuantity() <= 0) {
            cart.removeItem(cartItemRequest.getCode());
        } else {
            Product product =
                    productServiceClient
                            .getProductByCode(cartItemRequest.getCode())
                            .orElseThrow(
                                    () -> new ProductNotFoundException(cartItemRequest.getCode()));
            cart.updateItemQuantity(product.getCode(), cartItemRequest.getQuantity());
        }
        return cartRepository.save(cart);
    }

    public Cart removeCartItem(String cartId, String code) {
        Cart cart =
                cartRepository
                        .findById(cartId)
                        .orElseThrow(() -> new CartNotFoundException(cartId));
        log.info("Remove cart line item code: {}", code);
        cart.removeItem(code);
        return cartRepository.save(cart);
    }

    public void removeCart(String cartId) {
        cartRepository.deleteById(cartId);
    }
}
