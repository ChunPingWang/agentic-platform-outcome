// src/main/java/com/llmwebapp/inventory/application/StockCheckResult.java
package com.llmwebapp.inventory.application;

/**
 * 可用庫存檢查結果。
 *
 * @param sufficient 需求量是否可被可用庫存覆蓋
 * @param available  查詢當下的可用庫存
 */
public record StockCheckResult(boolean sufficient, int available) {
}