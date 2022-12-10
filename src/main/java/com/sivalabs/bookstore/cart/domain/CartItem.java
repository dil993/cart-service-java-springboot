package com.sivalabs.bookstore.cart.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public BigDecimal getSubTotal() {
        return price.multiply(new BigDecimal(quantity));
    }
}
