// src/main/java/com/llmwebapp/inventory/domain/repository/OptimisticLockException.java
package com.llmwebapp.inventory.domain.repository;

import com.llmwebapp.inventory.domain.ProductId;

/**
 * 樂觀鎖衝突例外。並行扣減時版本號不一致即拋出。
 */
public final class OptimisticLockException extends RuntimeException {

    public OptimisticLockException(ProductId productId) {
        super("樂觀鎖衝突: product=" + productId.value());
    }
}