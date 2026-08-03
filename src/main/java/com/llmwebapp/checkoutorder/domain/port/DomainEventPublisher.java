// src/main/java/com/llmwebapp/checkoutorder/domain/port/DomainEventPublisher.java
package com.llmwebapp.checkoutorder.domain.port;

import com.llmwebapp.checkoutorder.domain.event.DomainEvent;

/**
 * 領域事件發佈 Port。
 */
public interface DomainEventPublisher {

    void publish(DomainEvent event);
}