/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

public class OrderPromotion {
    private int id;
    private int orderId;
    private int promotionId;
    private BigDecimal appliedDiscountValue;

    public OrderPromotion() {
    }

    public OrderPromotion(int id, int orderId, int promotionId, BigDecimal appliedDiscountValue) {
        this.id = id;
        this.orderId = orderId;
        this.promotionId = promotionId;
        this.appliedDiscountValue = appliedDiscountValue;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public BigDecimal getAppliedDiscountValue() {
        return appliedDiscountValue;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public void setAppliedDiscountValue(BigDecimal appliedDiscountValue) {
        this.appliedDiscountValue = appliedDiscountValue;
    }

}
