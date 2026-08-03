// src/main/java/com/llmwebapp/shoppingcart/domain/port/AvailableStockProvider.java
package com.llmwebapp.shoppingcart.domain.port;

import com.llmwebapp.shoppingcart.domain.ProductId;

/**
 * 可用庫存查詢 Port(Shopping Cart → Inventory 的防腐層抽象)。
 *
 * <p>Cart 僅需「可用庫存數量」此一最小語意,實作由 infrastructure 提供的 ACL 轉接。</p>
 */
public interface AvailableStockProvider {

    /**
     * 查詢指定商品的可用庫存數量。
     */
    int availableStockOf(ProductId productId);
}