// src/main/java/com/llmwebapp/shoppingcart/infrastructure/InMemoryCartRepository.java
package com.llmwebapp.shoppingcart.infrastructure;

import com.llmwebapp.shoppingcart.domain.Cart;
import com.llmwebapp.shoppingcart.domain.CartId;
import com.llmwebapp.shoppingcart.domain.repository.CartRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CartRepository 的記憶體實作(供測試與示範用)。
 */
public final class InMemoryCartRepository implements CartRepository {

    private final Map<CartId, Cart> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Cart> findById(CartId cartId) {
        return Optional.ofNullable(store.get(cartId));
    }

    @Override
    public void save(Cart cart) {
        store.put(cart.id(), cart);
    }
}