// src/main/java/com/llmwebapp/shoppingcart/domain/Quantity.java
package com.llmwebapp.shoppingcart.domain;

/**
 * 數量(Value Object),必須為正整數。
 */
public record Quantity(int value) {

    public Quantity {
        if (value <= 0) {
            throw new IllegalArgumentException("數量必須為正整數,實際為: " + value);
        }
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    public Quantity plus(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public boolean greaterThan(int limit) {
        return this.value > limit;
    }
}