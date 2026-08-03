// src/main/java/com/llmwebapp/checkoutorder/domain/event/CheckoutSucceeded.java
package com.llmwebapp.checkoutorder.domain.event;

import com.llmwebapp.checkoutorder.domain.OrderId;

/**
 * 領域事件:結帳成功。
 */
public record CheckoutSucceeded(OrderId orderId) implements DomainEvent {
}