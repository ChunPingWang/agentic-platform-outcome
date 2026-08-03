// src/main/java/com/llmwebapp/inventory/domain/Inventory.java
package com.llmwebapp.inventory.domain;

import com.llmwebapp.inventory.domain.event.StockChecked;
import com.llmwebapp.inventory.domain.event.StockDeducted;

import java.util.Objects;

/**
 * 商品庫存聚合根(Aggregate Root)。
 *
 * <p>不變量:可用庫存不得為負。</p>
 * <p>提供可用庫存查詢、檢查(CheckAvailableStock)與扣減(DeductStock)。
 * 以樂觀鎖版本號支援並行防護。</p>
 */
public final class Inventory {

    private final ProductId productId;
    private StockQuantity available;
    private long version;

    public Inventory(ProductId productId, StockQuantity available) {
        this(productId, available, 0L);
    }

    public Inventory(ProductId productId, StockQuantity available, long version) {
        this.productId = Objects.requireNonNull(productId, "productId 不可為 null");
        this.available = Objects.requireNonNull(available, "available 不可為 null");
        this.version = version;
    }

    public ProductId productId() {
        return productId;
    }

    public int availableStock() {
        return available.value();
    }

    public long version() {
        return version;
    }

    /**
     * 檢查需求量是否可被可用庫存覆蓋(不變更庫存)。
     *
     * @return 庫存已檢查事件,其 sufficient 表示是否充足
     */
    public StockChecked check(int demand) {
        requirePositive(demand);
        boolean sufficient = available.canCover(demand);
        return new StockChecked(productId, demand, available.value(), sufficient);
    }

    /**
     * 條件式扣減:可用庫存足以覆蓋需求量才扣減,否則拋出 InsufficientStockException。
     * 扣減成功時遞增版本號(樂觀鎖),以確保並行防護與「可用庫存不得為負」不變量。
     *
     * @return 庫存已扣減事件
     */
    public StockDeducted deduct(int demand) {
        requirePositive(demand);
        if (!available.canCover(demand)) {
            throw new InsufficientStockException(productId, available.value(), demand);
        }
        this.available = available.minus(demand);
        this.version++;
        return new StockDeducted(productId, demand, available.value());
    }

    private static void requirePositive(int demand) {
        if (demand <= 0) {
            throw new IllegalArgumentException("需求量必須為正整數,實際為: " + demand);
        }
    }
}