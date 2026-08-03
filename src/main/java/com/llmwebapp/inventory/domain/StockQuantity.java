// src/main/java/com/llmwebapp/inventory/domain/StockQuantity.java
package com.llmwebapp.inventory.domain;

/**
 * 庫存數量(Value Object),不得為負。
 */
public record StockQuantity(int value) {

    public StockQuantity {
        if (value < 0) {
            throw new IllegalArgumentException("庫存數量不得為負,實際為: " + value);
        }
    }

    public static StockQuantity of(int value) {
        return new StockQuantity(value);
    }

    public boolean canCover(int demand) {
        return this.value >= demand;
    }

    public StockQuantity minus(int demand) {
        return new StockQuantity(this.value - demand);
    }
}