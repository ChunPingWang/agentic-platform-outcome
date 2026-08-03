// src/main/java/com/llmwebapp/checkoutorder/domain/event/OrderPlaced.java
package com.llmwebapp.checkoutorder.domain.event;

import com.llmwebapp.checkoutorder.domain.OrderId;

/**
 * 領域事件:訂單已建立。
 */
public record OrderPlaced(OrderId orderId, int lineCount) implements DomainEvent {
}