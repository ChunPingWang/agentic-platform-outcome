// src/main/java/com/llmwebapp/checkoutorder/domain/OrderId.java
package com.llmwebapp.checkoutorder.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * 訂單識別碼(Value Object)。
 */
public record OrderId(String value) {

    public OrderId {
        Objects.requireNonNull(value, "orderId 不可為 null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("orderId 不可為空白");
        }
    }

    public static OrderId of(String value) {
        return new OrderId(value);
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID().toString());
    }
}