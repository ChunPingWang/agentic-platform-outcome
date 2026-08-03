// src/main/java/com/llmwebapp/shoppingcart/domain/ProductId.java
package com.llmwebapp.shoppingcart.domain;

import java.util.Objects;

/**
 * 商品識別碼(Value Object)。
 */
public record ProductId(String value) {

    public ProductId {
        Objects.requireNonNull(value, "productId 不可為 null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("productId 不可為空白");
        }
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }
}