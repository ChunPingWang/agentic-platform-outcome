} catch (RuntimeException ex) {
    compensate(deducted);
    List<String> failed = List.of();   // ← 恆為空清單
    CheckoutRejected rejected = new CheckoutRejected(failed);