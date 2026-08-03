// src/main/java/com/llmwebapp/shoppingcart/domain/CartItem.java
package com.llmwebapp.shoppingcart.domain;

import java.util.Objects;

/**
 * 購物車品項(Entity,隸屬於 Cart 聚合)。
 */
public final class CartItem {

    private final ProductId productId;
    private Quantity quantity;

    public CartItem(ProductId productId, Quantity quantity) {
        this.productId = Objects.requireNonNull(productId, "productId 不可為 null");
        this.quantity = Objects.requireNonNull(quantity, "quantity 不可為 null");
    }

    public ProductId productId() {
        return productId;
    }

    public Quantity quantity() {
        return quantity;
    }

    void increaseBy(Quantity delta) {
        this.quantity = this.quantity.plus(delta);
    }
}