// src/main/java/com/llmwebapp/checkoutorder/domain/repository/OrderRepository.java
package com.llmwebapp.checkoutorder.domain.repository;

import com.llmwebapp.checkoutorder.domain.Order;

/**
 * 訂單儲存庫(Port)。
 */
public interface OrderRepository {

    void save(Order order);

    int count();
}