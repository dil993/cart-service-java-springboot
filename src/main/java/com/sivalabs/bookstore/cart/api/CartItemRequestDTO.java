package com.sivalabs.bookstore.cart.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequestDTO {
    @NotEmpty private String code;

    @Min(0)
    private int quantity;
}
