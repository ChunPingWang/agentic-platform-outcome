// src/main/java/com/llmwebapp/inventory/domain/port/DomainEventPublisher.java
package com.llmwebapp.inventory.domain.port;

import com.llmwebapp.inventory.domain.event.DomainEvent;

/**
 * 領域事件發佈 Port。
 */
public interface DomainEventPublisher {

    void publish(DomainEvent event);
}