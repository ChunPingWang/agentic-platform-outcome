// src/main/java/com/llmwebapp/checkoutorder/application/CheckoutResult.java
package com.llmwebapp.checkoutorder.application;

import com.llmwebapp.checkoutorder.domain.OrderId;

import java.util.List;
import java.util.Optional;

/**
 * 結帳結果。
 *
 * @param succeeded              是否結帳成功
 * @param orderId               成功時建立的訂單識別碼
 * @param insufficientProductIds 拒絕時的庫存不足品項
 */
public record CheckoutResult(
        boolean succeeded,
        Optional<OrderId> orderId,
        List<String> insufficientProductIds) {

    public static CheckoutResult success(OrderId orderId) {
        return new CheckoutResult(true, Optional.of(orderId), List.of());
    }

    public static CheckoutResult rejected(List<String> insufficientProductIds) {
        return new CheckoutResult(false, Optional.empty(), List.copyOf(insufficientProductIds));
    }
}