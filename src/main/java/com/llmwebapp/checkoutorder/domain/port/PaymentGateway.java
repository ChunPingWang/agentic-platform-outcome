// src/main/java/com/llmwebapp/checkoutorder/domain/port/PaymentGateway.java
package com.llmwebapp.checkoutorder.domain.port;

import com.llmwebapp.checkoutorder.domain.OrderId;

/**
 * 扣款 Port。整筆拒絕時不應被呼叫,以滿足「不扣款」。
 */
public interface PaymentGateway {

    void charge(OrderId orderId);
}