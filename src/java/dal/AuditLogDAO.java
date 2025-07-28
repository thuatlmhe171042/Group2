package dal;

import models.AuditLog;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditLogDAO {

    // Lấy toàn bộ log, JOIN với User
    public List<AuditLog> getAllAuditLogs() {
        List<AuditLog> list = new ArrayList<>();
        String sql = "SELECT al.*, u.Name, u.Email FROM AuditLogs al LEFT JOIN Users u ON al.UserId = u.Id ORDER BY al.Timestamp DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractAuditLogFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lọc log theo tiêu chí động
    public List<AuditLog> searchAuditLogs(String userKeyword, String action, String table, Date from, Date to) {
        List<AuditLog> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT al.*, u.Name, u.Email FROM AuditLogs al LEFT JOIN Users u ON al.UserId = u.Id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (userKeyword != null && !userKeyword.isEmpty()) {
            sql.append(" AND (u.Name LIKE ? OR u.Email LIKE ?)");
            params.add("%" + userKeyword + "%");
            params.add("%" + userKeyword + "%");
        }
        if (action != null && !action.isEmpty()) {
            sql.append(" AND al.ActionType = ?");
            params.add(action);
        }
        if (table != null && !table.isEmpty()) {
            sql.append(" AND al.TableName = ?");
            params.add(table);
        }
        if (from != null) {
            sql.append(" AND al.Timestamp >= ?");
            params.add(new java.sql.Timestamp(from.getTime()));
        }
        if (to != null) {
            sql.append(" AND al.Timestamp <= ?");
            params.add(new java.sql.Timestamp(to.getTime()));
        }
        sql.append(" ORDER BY al.Timestamp DESC");
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractAuditLogFromResultSet(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm log mới
    public void insertAuditLog(AuditLog log) {
        String sql = "INSERT INTO AuditLogs (UserId, ActionType, TableName, RecordId, ChangeDetails, IpAddress, Timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, log.getUser() != null ? log.getUser().getId() : null);
            ps.setString(2, log.getActionType());
            ps.setString(3, log.getTableName());
            ps.setInt(4, log.getRecordId());
            ps.setString(5, log.getChangeDetails());
            ps.setString(6, log.getIpAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addLog(int userId, String action, String table, int recordId, String details, String ip) {
        String sql = "INSERT INTO AuditLogs (UserId, ActionType, TableName, RecordId, ChangeDetails, IpAddress) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setString(3, table);
            ps.setInt(4, recordId);
            ps.setString(5, details);
            ps.setString(6, ip);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Hàm hỗ trợ: lấy AuditLog từ ResultSet
    private AuditLog extractAuditLogFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("UserId"));
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("Email"));

        AuditLog log = new AuditLog();
        log.setId(rs.getInt("Id"));
        log.setUser(user);
        log.setActionType(rs.getString("ActionType"));
        log.setTableName(rs.getString("TableName"));
        log.setRecordId(rs.getInt("RecordId"));
        log.setChangeDetails(rs.getString("ChangeDetails"));
        log.setIpAddress(rs.getString("IpAddress"));
        log.setTimestamp(rs.getTimestamp("Timestamp"));
        return log;
    }
}
