// src/main/java/com/llmwebapp/shoppingcart/domain/repository/CartRepository.java
package com.llmwebapp.shoppingcart.domain.repository;

import com.llmwebapp.shoppingcart.domain.Cart;
import com.llmwebapp.shoppingcart.domain.CartId;

import java.util.Optional;

/**
 * 購物車儲存庫(Port)。
 */
public interface CartRepository {

    Optional<Cart> findById(CartId cartId);

    void save(Cart cart);
}