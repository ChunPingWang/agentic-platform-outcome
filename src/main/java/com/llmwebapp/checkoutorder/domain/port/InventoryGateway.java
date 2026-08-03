// src/main/java/com/llmwebapp/checkoutorder/domain/port/InventoryGateway.java
package com.llmwebapp.checkoutorder.domain.port;

/**
 * Checkout → Inventory 的防腐層(ACL)Port。
 *
 * <p>將購物車品項轉為庫存檢查/扣減語意,隔離 Inventory 內部模型。</p>
 */
public interface InventoryGateway {

    /**
     * 檢查單一品項需求量是否可被可用庫存覆蓋(不變更庫存)。
     */
    boolean isStockSufficient(String productId, int demand);

    /**
     * 扣減單一品項庫存。庫存不足時應拋出例外,以維持「不部分成交」。
     */
    void deduct(String productId, int quantity);

    /**
     * 補償:回補先前已扣減的庫存(供部分扣減後失敗時回滾)。
     */
    void restore(String productId, int quantity);
}