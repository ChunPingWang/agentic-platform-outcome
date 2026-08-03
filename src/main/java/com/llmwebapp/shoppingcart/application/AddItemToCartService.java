// src/main/java/com/llmwebapp/shoppingcart/application/AddItemToCartService.java
package com.llmwebapp.shoppingcart.application;

import com.llmwebapp.shoppingcart.application.command.AddItemToCartCommand;
import com.llmwebapp.shoppingcart.domain.AddItemResult;
import com.llmwebapp.shoppingcart.domain.Cart;
import com.llmwebapp.shoppingcart.domain.CartId;
import com.llmwebapp.shoppingcart.domain.ProductId;
import com.llmwebapp.shoppingcart.domain.Quantity;
import com.llmwebapp.shoppingcart.domain.port.AvailableStockProvider;
import com.llmwebapp.shoppingcart.domain.port.DomainEventPublisher;
import com.llmwebapp.shoppingcart.domain.repository.CartRepository;

import java.util.Objects;

/**
 * 應用服務:處理「將商品加入購物車」用例。
 *
 * <p>交易邊界為單一 Cart 聚合。加入時以可用庫存查詢結果進行檢查,
 * 接受則儲存並發佈 ItemAddedToCart;拒絕則不異動並發佈 AddToCartRejected。</p>
 */
public final class AddItemToCartService {

    private final CartRepository cartRepository;
    private final AvailableStockProvider availableStockProvider;
    private final DomainEventPublisher eventPublisher;

    public AddItemToCartService(CartRepository cartRepository,
                                AvailableStockProvider availableStockProvider,
                                DomainEventPublisher eventPublisher) {
        this.cartRepository = Objects.requireNonNull(cartRepository, "cartRepository 不可為 null");
        this.availableStockProvider = Objects.requireNonNull(availableStockProvider, "availableStockProvider 不可為 null");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "eventPublisher 不可為 null");
    }

    public AddItemToCartResult handle(AddItemToCartCommand command) {
        Objects.requireNonNull(command, "command 不可為 null");

        CartId cartId = CartId.of(command.cartId());
        ProductId productId = ProductId.of(command.productId());
        Quantity quantity = Quantity.of(command.quantity());

        Cart cart = cartRepository.findById(cartId)
                .orElseGet(() -> new Cart(cartId));

        int availableStock = availableStockProvider.availableStockOf(productId);

        AddItemResult result = cart.addItem(productId, quantity, availableStock);

        if (result.accepted()) {
            cartRepository.save(cart);
        }
        eventPublisher.publish(result.event());

        return new AddItemToCartResult(result.accepted(), cart.quantityOf(productId));
    }
}