// src/main/java/com/llmwebapp/inventory/application/InventoryQueryService.java
package com.llmwebapp.inventory.application;

import com.llmwebapp.inventory.application.command.CheckAvailableStockCommand;
import com.llmwebapp.inventory.domain.Inventory;
import com.llmwebapp.inventory.domain.ProductId;
import com.llmwebapp.inventory.domain.event.StockChecked;
import com.llmwebapp.inventory.domain.port.DomainEventPublisher;
import com.llmwebapp.inventory.domain.repository.InventoryRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * 應用服務:可用庫存查詢與檢查(CheckAvailableStock)。
 *
 * <p>供上游(Cart 加入時、Checkout 再檢查時)查詢庫存,不變更庫存。
 * 商品不存在時視為可用庫存 0(不足)。</p>
 */
public final class InventoryQueryService {

    private final InventoryRepository inventoryRepository;
    private final DomainEventPublisher eventPublisher;

    public InventoryQueryService(InventoryRepository inventoryRepository,
                                 DomainEventPublisher eventPublisher) {
        this.inventoryRepository = Objects.requireNonNull(inventoryRepository, "inventoryRepository 不可為 null");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "eventPublisher 不可為 null");
    }

    /**
     * 查詢指定商品的可用庫存數量;商品不存在則回 0。
     */
    public int availableStockOf(ProductId productId) {
        return inventoryRepository.findById(productId)
                .map(Inventory::availableStock)
                .orElse(0);
    }

    /**
     * 檢查需求量是否可被可用庫存覆蓋,並發佈 StockChecked 事件。
     */
    public StockCheckResult check(CheckAvailableStockCommand command) {
        Objects.requireNonNull(command, "command 不可為 null");
        ProductId productId = ProductId.of(command.productId());

        Optional<Inventory> found = inventoryRepository.findById(productId);
        if (found.isEmpty()) {
            StockChecked event = new StockChecked(productId, command.demand(), 0, false);
            eventPublisher.publish(event);
            return new StockCheckResult(false, 0);
        }

        StockChecked event = found.get().check(command.demand());
        eventPublisher.publish(event);
        return new StockCheckResult(event.sufficient(), event.available());
    }
}