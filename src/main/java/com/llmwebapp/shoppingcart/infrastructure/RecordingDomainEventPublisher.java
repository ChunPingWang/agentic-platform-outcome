// src/main/java/com/llmwebapp/shoppingcart/infrastructure/RecordingDomainEventPublisher.java
package com.llmwebapp.shoppingcart.infrastructure;

import com.llmwebapp.shoppingcart.domain.event.DomainEvent;
import com.llmwebapp.shoppingcart.domain.port.DomainEventPublisher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DomainEventPublisher 的記錄型實作(供測試斷言事件用)。
 */
public final class RecordingDomainEventPublisher implements DomainEventPublisher {

    private final List<DomainEvent> published = new CopyOnWriteArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        published.add(event);
    }

    public List<DomainEvent> published() {
        return List.copyOf(published);
    }
}