/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

public class AuditLog {
    private int id;
    private Integer userId;
    private String actionType;
    private String tableName;
    private Integer recordId;
    private String changeDetails;
    private String ipAddress;
    private LocalDateTime timestamp;

    public AuditLog() {
    }

    public AuditLog(int id, Integer userId, String actionType, String tableName, Integer recordId, String changeDetails, String ipAddress, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.actionType = actionType;
        this.tableName = tableName;
        this.recordId = recordId;
        this.changeDetails = changeDetails;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getTableName() {
        return tableName;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getChangeDetails() {
        return changeDetails;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setChangeDetails(String changeDetails) {
        this.changeDetails = changeDetails;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
