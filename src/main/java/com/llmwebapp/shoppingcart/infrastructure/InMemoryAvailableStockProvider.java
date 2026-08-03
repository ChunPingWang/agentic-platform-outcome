// src/main/java/com/llmwebapp/shoppingcart/infrastructure/InMemoryAvailableStockProvider.java
package com.llmwebapp.shoppingcart.infrastructure;

import com.llmwebapp.shoppingcart.domain.ProductId;
import com.llmwebapp.shoppingcart.domain.port.AvailableStockProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AvailableStockProvider 的記憶體實作(ACL 佔位)。
 *
 * <p>正式環境應由 Inventory Context 的查詢介面提供,此處以本地表模擬,
 * 隔離 Cart 與 Inventory 的內部模型。</p>
 */
public final class InMemoryAvailableStockProvider implements AvailableStockProvider {

    private final Map<ProductId, Integer> stock = new ConcurrentHashMap<>();

    public void setStock(ProductId productId, int available) {
        stock.put(productId, available);
    }

    @Override
    public int availableStockOf(ProductId productId) {
        return stock.getOrDefault(productId, 0);
    }
}