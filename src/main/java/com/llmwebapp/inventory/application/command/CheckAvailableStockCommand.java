// src/main/java/com/llmwebapp/inventory/application/command/CheckAvailableStockCommand.java
package com.llmwebapp.inventory.application.command;

import java.util.Objects;

/**
 * 命令:檢查可用庫存。
 */
public record CheckAvailableStockCommand(String productId, int demand) {

    public CheckAvailableStockCommand {
        Objects.requireNonNull(productId, "productId 不可為 null");
    }
}