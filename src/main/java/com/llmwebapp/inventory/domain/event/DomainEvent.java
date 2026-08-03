// src/main/java/com/llmwebapp/inventory/domain/event/DomainEvent.java
package com.llmwebapp.inventory.domain.event;

/**
 * 領域事件標記介面。
 */
public sealed interface DomainEvent
        permits StockChecked, StockDeducted {
}