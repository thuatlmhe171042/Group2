package models;

import java.util.Date;

public class AuditLog {
    private int id;
    private User user; // Gán luôn object User để dễ truy xuất tên nhân viên
    private String actionType;
    private String tableName;
    private int recordId;
    private String changeDetails;
    private String ipAddress;
    private Date timestamp;

    public AuditLog() {}

    public AuditLog(int id, User user, String actionType, String tableName, int recordId, String changeDetails, String ipAddress, Date timestamp) {
        this.id = id;
        this.user = user;
        this.actionType = actionType;
        this.tableName = tableName;
        this.recordId = recordId;
        this.changeDetails = changeDetails;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getChangeDetails() {
        return changeDetails;
    }

    public void setChangeDetails(String changeDetails) {
        this.changeDetails = changeDetails;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    
}
