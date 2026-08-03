// src/main/java/com/llmwebapp/shoppingcart/domain/CartId.java
package com.llmwebapp.shoppingcart.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * 購物車識別碼(Value Object)。
 */
public record CartId(String value) {

    public CartId {
        Objects.requireNonNull(value, "cartId 不可為 null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("cartId 不可為空白");
        }
    }

    public static CartId of(String value) {
        return new CartId(value);
    }

    public static CartId newId() {
        return new CartId(UUID.randomUUID().toString());
    }
}