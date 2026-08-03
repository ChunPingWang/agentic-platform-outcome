// src/main/java/com/llmwebapp/shoppingcart/domain/event/AddToCartRejected.java
package com.llmwebapp.shoppingcart.domain.event;

import com.llmwebapp.shoppingcart.domain.CartId;
import com.llmwebapp.shoppingcart.domain.ProductId;

/**
 * 領域事件:加入購物車已被拒絕(累計數量超過可用庫存)。
 */
public record AddToCartRejected(
        CartId cartId,
        ProductId productId,
        int requestedQuantity,
        int existingQuantity,
        int availableStock) implements DomainEvent {
}