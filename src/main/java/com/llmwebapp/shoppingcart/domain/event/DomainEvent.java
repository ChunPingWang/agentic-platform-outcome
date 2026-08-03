// src/main/java/com/llmwebapp/shoppingcart/domain/event/DomainEvent.java
package com.llmwebapp.shoppingcart.domain.event;

/**
 * 領域事件標記介面。
 */
public sealed interface DomainEvent
        permits ItemAddedToCart, AddToCartRejected {
}