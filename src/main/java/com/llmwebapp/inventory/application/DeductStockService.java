// src/main/java/com/llmwebapp/inventory/application/DeductStockService.java
package com.llmwebapp.inventory.application;

import com.llmwebapp.inventory.application.command.DeductStockCommand;
import com.llmwebapp.inventory.domain.Inventory;
import com.llmwebapp.inventory.domain.ProductId;
import com.llmwebapp.inventory.domain.event.StockDeducted;
import com.llmwebapp.inventory.domain.port.DomainEventPublisher;
import com.llmwebapp.inventory.domain.repository.InventoryRepository;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 應用服務:扣減庫存(DeductStock)。
 *
 * <p>供結帳成交時扣減,採條件式扣減(可用庫存 - 需求量 ≥ 0)與樂觀鎖,
 * 保證「可用庫存不得為負」之不變量並防止超賣。</p>
 */
public final class DeductStockService {

    private final InventoryRepository inventoryRepository;
    private final DomainEventPublisher eventPublisher;

    public DeductStockService(InventoryRepository inventoryRepository,
                              DomainEventPublisher eventPublisher) {
        this.inventoryRepository = Objects.requireNonNull(inventoryRepository, "inventoryRepository 不可為 null");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "eventPublisher 不可為 null");
    }

    public StockDeductionResult deduct(DeductStockCommand command) {
        Objects.requireNonNull(command, "command 不可為 null");
        ProductId productId = ProductId.of(command.productId());

        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("找不到商品庫存: " + command.productId()));

        StockDeducted event = inventory.deduct(command.quantity());
        inventoryRepository.save(inventory);
        eventPublisher.publish(event);

        return new StockDeductionResult(event.remainingStock());
    }
}