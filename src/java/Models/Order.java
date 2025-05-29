/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Order {
    private int id;
    private Integer userId;
    private LocalDateTime orderTime;
    private BigDecimal originalAmount;
    private BigDecimal discountAmount;
    private String status;

    public Order() {
    }

    public Order(int id, Integer userId, LocalDateTime orderTime, BigDecimal originalAmount, BigDecimal discountAmount, String status) {
        this.id = id;
        this.userId = userId;
        this.orderTime = orderTime;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
