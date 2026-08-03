// src/main/java/com/llmwebapp/shoppingcart/domain/port/DomainEventPublisher.java
package com.llmwebapp.shoppingcart.domain.port;

import com.llmwebapp.shoppingcart.domain.event.DomainEvent;

/**
 * 領域事件發佈 Port。
 */
public interface DomainEventPublisher {

    void publish(DomainEvent event);
}