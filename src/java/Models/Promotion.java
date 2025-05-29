/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Promotion {
    private int id;
    private String promotionCode;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimit;
    private int currentUsageCount;
    private String status;
    private LocalDateTime createdAt;
    private Integer createdBy;

    public Promotion() {
    }

    public Promotion(int id, String promotionCode, String name, String description, String discountType, BigDecimal discountValue, LocalDateTime startDate, LocalDateTime endDate, Integer usageLimit, int currentUsageCount, String status, LocalDateTime createdAt, Integer createdBy) {
        this.id = id;
        this.promotionCode = promotionCode;
        this.name = name;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimit = usageLimit;
        this.currentUsageCount = currentUsageCount;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public int getCurrentUsageCount() {
        return currentUsageCount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public void setCurrentUsageCount(int currentUsageCount) {
        this.currentUsageCount = currentUsageCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
