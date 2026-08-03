// src/main/java/com/llmwebapp/checkoutorder/domain/event/DomainEvent.java
package com.llmwebapp.checkoutorder.domain.event;

/**
 * 領域事件標記介面。
 */
public sealed interface DomainEvent
        permits CheckoutSucceeded, CheckoutRejected, OrderPlaced {
}