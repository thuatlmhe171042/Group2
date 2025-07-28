package models;

import java.util.Date;

public class EmailVerification {
    private int id;
    private Integer userId;      // Có thể null khi chưa đăng ký
    private String email;
    private String otpCode;
    private String actionType;   // 'register', 'reset_password', 'change_password'
    private Date createdAt;
    private Date expiresAt;
    private boolean isUsed;

    public EmailVerification() {}

    public EmailVerification(int id, Integer userId, String email, String otpCode, String actionType, Date createdAt, Date expiresAt, boolean isUsed) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.otpCode = otpCode;
        this.actionType = actionType;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isUsed = isUsed;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }

    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean used) { isUsed = used; }
}
