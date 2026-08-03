// src/main/java/com/llmwebapp/checkoutorder/infrastructure/InMemoryOrderRepository.java
package com.llmwebapp.checkoutorder.infrastructure;

import com.llmwebapp.checkoutorder.domain.Order;
import com.llmwebapp.checkoutorder.domain.OrderId;
import com.llmwebapp.checkoutorder.domain.repository.OrderRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OrderRepository 的記憶體實作(供測試與示範用)。
 */
public final class InMemoryOrderRepository implements OrderRepository {

    private final Map<OrderId, Order> store = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.id(), order);
    }

    @Override
    public int count() {
        return store.size();
    }
}