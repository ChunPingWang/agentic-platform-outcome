// src/main/java/com/llmwebapp/checkoutorder/domain/event/CheckoutRejected.java
package com.llmwebapp.checkoutorder.domain.event;

import java.util.List;

/**
 * 領域事件:結帳已被整筆拒絕。
 *
 * @param insufficientProductIds 導致拒絕的庫存不足品項
 */
public record CheckoutRejected(List<String> insufficientProductIds) implements DomainEvent {

    public CheckoutRejected {
        insufficientProductIds = List.copyOf(insufficientProductIds);
    }
}