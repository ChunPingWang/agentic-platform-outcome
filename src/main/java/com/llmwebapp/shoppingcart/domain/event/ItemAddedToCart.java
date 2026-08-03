// src/main/java/com/llmwebapp/shoppingcart/domain/event/ItemAddedToCart.java
package com.llmwebapp.shoppingcart.domain.event;

import com.llmwebapp.shoppingcart.domain.CartId;
import com.llmwebapp.shoppingcart.domain.ProductId;

/**
 * 領域事件:商品已加入購物車。
 *
 * @param resultingQuantity 加入後該品項在購物車中的數量
 */
public record ItemAddedToCart(
        CartId cartId,
        ProductId productId,
        int addedQuantity,
        int resultingQuantity) implements DomainEvent {
}