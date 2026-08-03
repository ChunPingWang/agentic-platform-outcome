// src/main/java/com/llmwebapp/checkoutorder/infrastructure/RecordingPaymentGateway.java
package com.llmwebapp.checkoutorder.infrastructure;

import com.llmwebapp.checkoutorder.domain.OrderId;
import com.llmwebapp.checkoutorder.domain.port.PaymentGateway;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PaymentGateway 的記錄型實作(供測試斷言是否扣款用)。
 */
public final class RecordingPaymentGateway implements PaymentGateway {

    private final List<OrderId> charges = new CopyOnWriteArrayList<>();

    @Override
    public void charge(OrderId orderId) {
        charges.add(orderId);
    }

    public List<OrderId> charges() {
        return List.copyOf(charges);
    }

    public boolean charged() {
        return !charges.isEmpty();
    }
}