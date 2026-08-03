// src/main/java/com/llmwebapp/inventory/application/command/DeductStockCommand.java
package com.llmwebapp.inventory.application.command;

import java.util.Objects;

/**
 * 命令:扣減庫存。
 */
public record DeductStockCommand(String productId, int quantity) {

    public DeductStockCommand {
        Objects.requireNonNull(productId, "productId 不可為 null");
    }
}