// src/main/java/com/llmwebapp/shoppingcart/domain/AddItemResult.java
package com.llmwebapp.shoppingcart.domain;

import com.llmwebapp.shoppingcart.domain.event.DomainEvent;

import java.util.Objects;

/**
 * 加入購物車的領域運算結果。
 * 承載是否接受,以及對應的領域事件(ItemAddedToCart 或 AddToCartRejected)。
 */
public record AddItemResult(boolean accepted, DomainEvent event) {

    public AddItemResult {
        Objects.requireNonNull(event, "event 不可為 null");
    }
}