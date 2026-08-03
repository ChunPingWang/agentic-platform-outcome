orderRepository.save(order);
paymentGateway.charge(orderId);   // ← 若扣款失敗,庫存已扣、訂單已存,無補償
eventPublisher.publish(new OrderPlaced(...));