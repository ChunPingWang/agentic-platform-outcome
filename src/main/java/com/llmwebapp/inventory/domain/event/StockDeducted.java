// src/main/java/com/llmwebapp/inventory/domain/event/StockDeducted.java
package com.llmwebapp.inventory.domain.event;

import com.llmwebapp.inventory.domain.ProductId;

/**
 * 領域事件:庫存已扣減。
 */
public record StockDeducted(
        ProductId productId,
        int deductedQuantity,
        int remainingStock) implements DomainEvent {
}