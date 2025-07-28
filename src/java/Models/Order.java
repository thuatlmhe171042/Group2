package models;

public class Order {
    private int id;
    private String orderCode;
    private Integer userId;
    private String email;
    private String phoneNumber;
    private String orderTime;
    private double originalAmount;
    private double discountAmount;
    private String status;
    private String updatedAt;
    private boolean isDeleted;

    public Order() {}

    public Order(int id, String orderCode, Integer userId, String email, String phoneNumber,
                 String orderTime, double originalAmount, double discountAmount,
                 String status, String updatedAt, boolean isDeleted) {
        this.id = id;
        this.orderCode = orderCode;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderTime = orderTime;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.status = status;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
