// src/main/java/com/llmwebapp/shoppingcart/application/AddItemToCartResult.java
package com.llmwebapp.shoppingcart.application;

/**
 * 加入購物車的應用層結果。
 *
 * @param accepted          是否接受加入
 * @param resultingQuantity 加入後該品項在購物車中的數量(拒絕時為既有數量)
 */
public record AddItemToCartResult(boolean accepted, int resultingQuantity) {
}