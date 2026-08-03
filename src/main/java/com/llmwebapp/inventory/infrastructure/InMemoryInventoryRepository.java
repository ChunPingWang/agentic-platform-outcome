// src/main/java/com/llmwebapp/inventory/infrastructure/InMemoryInventoryRepository.java
package com.llmwebapp.inventory.infrastructure;

import com.llmwebapp.inventory.domain.Inventory;
import com.llmwebapp.inventory.domain.ProductId;
import com.llmwebapp.inventory.domain.StockQuantity;
import com.llmwebapp.inventory.domain.repository.InventoryRepository;
import com.llmwebapp.inventory.domain.repository.OptimisticLockException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InventoryRepository 的記憶體實作,以版本號模擬樂觀鎖(供測試與示範用)。
 *
 * <p>save 時比對儲存版本:若與被載入時的版本不一致(表示期間已被他人更新),
 * 則拋出 OptimisticLockException;否則以遞增後版本寫入。</p>
 */
public final class InMemoryInventoryRepository implements InventoryRepository {

    private record Snapshot(int available, long version) {
    }

    private final Map<ProductId, Snapshot> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Inventory> findById(ProductId productId) {
        Snapshot snapshot = store.get(productId);
        if (snapshot == null) {
            return Optional.empty();
        }
        return Optional.of(new Inventory(productId,
                StockQuantity.of(snapshot.available()), snapshot.version()));
    }

    @Override
    public void save(Inventory inventory) {
        ProductId productId = inventory.productId();
        long newVersion = inventory.version();
        long expectedPrevVersion = newVersion - 1L;

        store.compute(productId, (key, current) -> {
            long currentVersion = current == null ? 0L : current.version();
            if (current != null && currentVersion != expectedPrevVersion) {
                throw new OptimisticLockException(productId);
            }
            return new Snapshot(inventory.availableStock(), newVersion);
        });
    }

    /**
     * 測試/初始化用:直接設定商品可用庫存(版本歸零)。
     */
    public void setStock(ProductId productId, int available) {
        store.put(productId, new Snapshot(available, 0L));
    }
}