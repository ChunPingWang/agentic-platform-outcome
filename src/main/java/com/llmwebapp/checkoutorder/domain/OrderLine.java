// src/main/java/com/llmwebapp/checkoutorder/domain/OrderLine.java
package com.llmwebapp.checkoutorder.domain;

import java.util.Objects;

/**
 * 訂單明細(Value Object,隸屬於 Order 聚合)。
 */
public record OrderLine(String productId, int quantity) {

    public OrderLine {
        Objects.requireNonNull(productId, "productId 不可為 null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("數量必須為正整數,實際為: " + quantity);
        }
    }
}