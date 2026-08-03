// src/main/java/com/llmwebapp/checkoutorder/application/command/CheckoutCommand.java
package com.llmwebapp.checkoutorder.application.command;

import com.llmwebapp.checkoutorder.domain.CheckoutLine;

import java.util.List;
import java.util.Objects;

/**
 * 命令:進行結帳。攜帶結帳當下的購物車品項快照。
 */
public record CheckoutCommand(List<CheckoutLine> lines) {

    public CheckoutCommand {
        Objects.requireNonNull(lines, "lines 不可為 null");
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("結帳品項不可為空");
        }
        lines = List.copyOf(lines);
    }
}