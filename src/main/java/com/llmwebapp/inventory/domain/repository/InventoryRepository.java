// src/main/java/com/llmwebapp/inventory/domain/repository/InventoryRepository.java
package com.llmwebapp.inventory.domain.repository;

import com.llmwebapp.inventory.domain.Inventory;
import com.llmwebapp.inventory.domain.ProductId;

import java.util.Optional;

/**
 * 庫存儲存庫(Port)。save 實作應以版本號進行樂觀鎖檢查。
 */
public interface InventoryRepository {

    Optional<Inventory> findById(ProductId productId);

    void save(Inventory inventory);
}