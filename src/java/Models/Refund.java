/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Refund {
    private int id;
    private int ticketId; 
    private int processedBy;
    private LocalDateTime refundTime;
    private BigDecimal refundAmount;
    private String reason;

    public Refund() {
    }

    public Refund(int id, int ticketId, int processedBy, LocalDateTime refundTime, BigDecimal refundAmount, String reason) {
        this.id = id;
        this.ticketId = ticketId;
        this.processedBy = processedBy;
        this.refundTime = refundTime;
        this.refundAmount = refundAmount;
        this.reason = reason;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getProcessedBy() {
        return processedBy;
    }

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public String getReason() {
        return reason;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setProcessedBy(int processedBy) {
        this.processedBy = processedBy;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
