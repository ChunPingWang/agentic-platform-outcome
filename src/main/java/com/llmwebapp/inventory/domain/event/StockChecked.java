// src/main/java/com/llmwebapp/inventory/domain/event/StockChecked.java
package com.llmwebapp.inventory.domain.event;

import com.llmwebapp.inventory.domain.ProductId;

/**
 * 領域事件:庫存已檢查。
 *
 * @param sufficient 需求量是否可被可用庫存覆蓋
 */
public record StockChecked(
        ProductId productId,
        int demand,
        int available,
        boolean sufficient) implements DomainEvent {
}