// src/main/java/com/llmwebapp/inventory/domain/InsufficientStockException.java
package com.llmwebapp.inventory.domain;

/**
 * 庫存不足例外。扣減時可用庫存無法覆蓋需求量即拋出,用以保證「不部分成交」。
 */
public final class InsufficientStockException extends RuntimeException {

    private final ProductId productId;
    private final int available;
    private final int demand;

    public InsufficientStockException(ProductId productId, int available, int demand) {
        super("庫存不足: product=" + productId.value()
                + ", available=" + available + ", demand=" + demand);
        this.productId = productId;
        this.available = available;
        this.demand = demand;
    }

    public ProductId productId() {
        return productId;
    }

    public int available() {
        return available;
    }

    public int demand() {
        return demand;
    }
}