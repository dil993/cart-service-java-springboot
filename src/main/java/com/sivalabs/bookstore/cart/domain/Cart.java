package com.sivalabs.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("carts")
public class Cart {
    @Id private String id;
    private Set<CartItem> items = new HashSet<>();

    public Cart(String id) {
        this.id = id;
        this.items = new HashSet<>();
    }

    public static Cart withNewId() {
        return new Cart(UUID.randomUUID().toString());
    }

    public void addItem(CartItem item) {
        for (CartItem cartItem : items) {
            if (cartItem.getCode().equals(item.getCode())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return;
            }
        }
        this.items.add(item);
    }

    public void updateItemQuantity(String code, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getCode().equals(code)) {
                cartItem.setQuantity(quantity);
            }
        }
    }

    public void removeItem(String code) {
        CartItem item = null;
        for (CartItem cartItem : items) {
            if (cartItem.getCode().equals(code)) {
                item = cartItem;
                break;
            }
        }
        if (item != null) {
            items.remove(item);
        }
    }

    public void clearItems() {
        items = new HashSet<>();
    }

    public BigDecimal getCartTotal() {
        return items.stream().map(CartItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
