// src/main/java/com/llmwebapp/checkoutorder/application/CheckoutService.java
package com.llmwebapp.checkoutorder.application;

import com.llmwebapp.checkoutorder.application.command.CheckoutCommand;
import com.llmwebapp.checkoutorder.domain.CheckoutLine;
import com.llmwebapp.checkoutorder.domain.Order;
import com.llmwebapp.checkoutorder.domain.OrderId;
import com.llmwebapp.checkoutorder.domain.OrderLine;
import com.llmwebapp.checkoutorder.domain.event.CheckoutRejected;
import com.llmwebapp.checkoutorder.domain.event.CheckoutSucceeded;
import com.llmwebapp.checkoutorder.domain.event.OrderPlaced;
import com.llmwebapp.checkoutorder.domain.port.DomainEventPublisher;
import com.llmwebapp.checkoutorder.domain.port.InventoryGateway;
import com.llmwebapp.checkoutorder.domain.port.PaymentGateway;
import com.llmwebapp.checkoutorder.domain.repository.OrderRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * 應用服務:結帳流程協調(Checkout)。
 *
 * <p>採「先檢查後扣減」兩段式,達成一致成交:</p>
 * <ol>
 *   <li>對全品項再次檢查庫存;任一不足即整筆拒絕(不扣款、不建立訂單、不扣減),發佈 CheckoutRejected。</li>
 *   <li>全數充足才逐項扣減、扣款並建立訂單,發佈 CheckoutSucceeded 與 OrderPlaced。</li>
 * </ol>
 * <p>扣減階段若中途失敗,對已扣減品項執行補償回補,確保「不部分成交」。</p>
 */
public final class CheckoutService {

    private final InventoryGateway inventoryGateway;
    private final PaymentGateway paymentGateway;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    public CheckoutService(InventoryGateway inventoryGateway,
                           PaymentGateway paymentGateway,
                           OrderRepository orderRepository,
                           DomainEventPublisher eventPublisher) {
        this.inventoryGateway = Objects.requireNonNull(inventoryGateway, "inventoryGateway 不可為 null");
        this.paymentGateway = Objects.requireNonNull(paymentGateway, "paymentGateway 不可為 null");
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository 不可為 null");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "eventPublisher 不可為 null");
    }

    public CheckoutResult checkout(CheckoutCommand command) {
        Objects.requireNonNull(command, "command 不可為 null");
        List<CheckoutLine> lines = command.lines();

        // 階段一:對全品項再次檢查庫存(前置守門)
        List<String> insufficient = new ArrayList<>();
        for (CheckoutLine line : lines) {
            if (!inventoryGateway.isStockSufficient(line.productId(), line.quantity())) {
                insufficient.add(line.productId());
            }
        }
        if (!insufficient.isEmpty()) {
            // 整筆拒絕:不扣款、不建立訂單、不扣減庫存
            CheckoutRejected rejected = new CheckoutRejected(insufficient);
            eventPublisher.publish(rejected);
            return CheckoutResult.rejected(insufficient);
        }

        // 階段二:全數充足才扣減;中途失敗則補償已扣減者
        Deque<CheckoutLine> deducted = new ArrayDeque<>();
        try {
            for (CheckoutLine line : lines) {
                inventoryGateway.deduct(line.productId(), line.quantity());
                deducted.push(line);
            }
        } catch (RuntimeException ex) {
            compensate(deducted);
            List<String> failed = List.of();
            CheckoutRejected rejected = new CheckoutRejected(failed);
            eventPublisher.publish(rejected);
            return CheckoutResult.rejected(failed);
        }

        // 建立訂單並扣款
        OrderId orderId = OrderId.newId();
        Order order = Order.place(orderId, toOrderLines(lines));
        orderRepository.save(order);
        paymentGateway.charge(orderId);

        eventPublisher.publish(new OrderPlaced(orderId, order.lines().size()));
        eventPublisher.publish(new CheckoutSucceeded(orderId));

        return CheckoutResult.success(orderId);
    }

    private void compensate(Deque<CheckoutLine> deducted) {
        while (!deducted.isEmpty()) {
            CheckoutLine line = deducted.pop();
            inventoryGateway.restore(line.productId(), line.quantity());
        }
    }

    private List<OrderLine> toOrderLines(List<CheckoutLine> lines) {
        List<OrderLine> orderLines = new ArrayList<>(lines.size());
        for (CheckoutLine line : lines) {
            orderLines.add(new OrderLine(line.productId(), line.quantity()));
        }
        return orderLines;
    }
}