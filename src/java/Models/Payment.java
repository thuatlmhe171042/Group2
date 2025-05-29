/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Payment {
    private int id;
    private int orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String transactionId;
    private LocalDateTime paymentTime;
    private String status; 
    private String gatewayResponse;

    public Payment() {
    }

    public Payment(int id, int orderId, String paymentMethod, BigDecimal amount, String transactionId, LocalDateTime paymentTime, String status, String gatewayResponse) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.transactionId = transactionId;
        this.paymentTime = paymentTime;
        this.status = status;
        this.gatewayResponse = gatewayResponse;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public String getStatus() {
        return status;
    }

    public String getGatewayResponse() {
        return gatewayResponse;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGatewayResponse(String gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

}
