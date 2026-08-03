// src/main/java/com/llmwebapp/checkoutorder/domain/Order.java
package com.llmwebapp.checkoutorder.domain;

import java.util.List;
import java.util.Objects;

/**
 * 訂單聚合根(Aggregate Root)。
 *
 * <p>不變量:訂單至少含一筆明細。訂單建立即代表結帳成交。</p>
 */
public final class Order {

    private final OrderId id;
    private final List<OrderLine> lines;

    private Order(OrderId id, List<OrderLine> lines) {
        this.id = Objects.requireNonNull(id, "orderId 不可為 null");
        Objects.requireNonNull(lines, "lines 不可為 null");
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("訂單至少需含一筆明細");
        }
        this.lines = List.copyOf(lines);
    }

    /**
     * 建立訂單(PlaceOrder)。
     */
    public static Order place(OrderId id, List<OrderLine> lines) {
        return new Order(id, lines);
    }

    public OrderId id() {
        return id;
    }

    public List<OrderLine> lines() {
        return lines;
    }
}