// src/main/java/com/llmwebapp/checkoutorder/domain/CheckoutLine.java
package com.llmwebapp.checkoutorder.domain;

import java.util.Objects;

/**
 * 結帳品項(Value Object):結帳當下購物車某品項的商品與數量快照。
 */
public record CheckoutLine(String productId, int quantity) {

    public CheckoutLine {
        Objects.requireNonNull(productId, "productId 不可為 null");
        if (productId.isBlank()) {
            throw new IllegalArgumentException("productId 不可為空白");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("數量必須為正整數,實際為: " + quantity);
        }
    }
}