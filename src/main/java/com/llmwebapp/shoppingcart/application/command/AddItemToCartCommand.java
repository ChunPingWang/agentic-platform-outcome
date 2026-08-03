// src/main/java/com/llmwebapp/shoppingcart/application/command/AddItemToCartCommand.java
package com.llmwebapp.shoppingcart.application.command;

import java.util.Objects;

/**
 * 命令:將商品加入購物車。
 */
public record AddItemToCartCommand(String cartId, String productId, int quantity) {

    public AddItemToCartCommand {
        Objects.requireNonNull(cartId, "cartId 不可為 null");
        Objects.requireNonNull(productId, "productId 不可為 null");
    }
}