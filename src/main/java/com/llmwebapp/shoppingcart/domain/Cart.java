// src/main/java/com/llmwebapp/shoppingcart/domain/Cart.java
package com.llmwebapp.shoppingcart.domain;

import com.llmwebapp.shoppingcart.domain.event.AddToCartRejected;
import com.llmwebapp.shoppingcart.domain.event.ItemAddedToCart;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 購物車聚合根(Aggregate Root)。
 *
 * <p>不變量:加入後累計數量不得超過「加入當下的可用庫存」;等於上限視為允許。</p>
 * <p>拒絕加入時,購物車數量維持不變(不異動)。</p>
 */
public final class Cart {

    private final CartId id;
    private final Map<ProductId, CartItem> items = new LinkedHashMap<>();

    public Cart(CartId id) {
        this.id = Objects.requireNonNull(id, "cartId 不可為 null");
    }

    public CartId id() {
        return id;
    }

    /**
     * 嘗試加入商品。以「既有數量 + 本次加入數量」的累計數量與可用庫存比較,
     * 超過即拒絕(不異動);等於或小於即接受。
     *
     * @param productId      商品
     * @param quantity       本次加入數量
     * @param availableStock 加入當下的可用庫存(由上游 Inventory 查詢提供)
     * @return 加入結果與對應領域事件
     */
    public AddItemResult addItem(ProductId productId, Quantity quantity, int availableStock) {
        Objects.requireNonNull(productId, "productId 不可為 null");
        Objects.requireNonNull(quantity, "quantity 不可為 null");
        if (availableStock < 0) {
            throw new IllegalArgumentException("availableStock 不可為負,實際為: " + availableStock);
        }

        int existing = quantityOf(productId);
        Quantity resulting = Quantity.of(existing).plus(quantity);

        if (resulting.greaterThan(availableStock)) {
            return new AddItemResult(false,
                    new AddToCartRejected(id, productId, quantity.value(), existing, availableStock));
        }

        CartItem item = items.get(productId);
        if (item == null) {
            items.put(productId, new CartItem(productId, quantity));
        } else {
            item.increaseBy(quantity);
        }

        return new AddItemResult(true,
                new ItemAddedToCart(id, productId, quantity.value(), resulting.value()));
    }

    /**
     * 取得指定商品在購物車中的數量;不存在則回 0。
     */
    public int quantityOf(ProductId productId) {
        CartItem item = items.get(productId);
        return item == null ? 0 : item.quantity().value();
    }

    public boolean contains(ProductId productId) {
        return items.containsKey(productId);
    }

    public Optional<CartItem> findItem(ProductId productId) {
        return Optional.ofNullable(items.get(productId));
    }

    public Map<ProductId, CartItem> items() {
        return Collections.unmodifiableMap(items);
    }
}