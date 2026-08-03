// src/main/java/com/llmwebapp/inventory/application/StockDeductionResult.java
package com.llmwebapp.inventory.application;

/**
 * 扣減庫存結果。
 *
 * @param remainingStock 扣減後剩餘可用庫存
 */
public record StockDeductionResult(int remainingStock) {
}