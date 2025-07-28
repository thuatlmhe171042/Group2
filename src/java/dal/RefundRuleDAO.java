package dal;

import models.RefundRule;
import java.sql.*;
import java.util.*;

public class RefundRuleDAO {
    
    public RefundRule findApplicableRule(int passengerTypeId, int carriageTypeId, Timestamp departureTime) {
    String sql = """
        SELECT TOP 1 * FROM RefundRules
        WHERE IsActive = 1
        AND (ApplyToPassengerTypeId IS NULL OR ApplyToPassengerTypeId = ?)
        AND (ApplyToCarriageTypeId IS NULL OR ApplyToCarriageTypeId = ?)
        AND DATEDIFF(HOUR, GETDATE(), ?) >= ApplyBeforeHours
        ORDER BY ApplyBeforeHours ASC
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, passengerTypeId);
        ps.setInt(2, carriageTypeId);
        ps.setTimestamp(3, departureTime);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new RefundRule(
                    rs.getInt("Id"),
                    rs.getString("RuleName"),
                    rs.getInt("ApplyBeforeHours"),
                    rs.getDouble("RefundPercentage"),
                    (Integer)rs.getObject("ApplyToPassengerTypeId"),
                    (Integer)rs.getObject("ApplyToCarriageTypeId"),
                    rs.getString("Note"),
                    rs.getBoolean("IsActive")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


    public List<RefundRule> getAllActiveRules() {
        List<RefundRule> list = new ArrayList<>();
        String sql = "SELECT * FROM RefundRules WHERE IsActive = 1";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RefundRule rule = new RefundRule();
                rule.setId(rs.getInt("Id"));
                rule.setRuleName(rs.getString("RuleName"));
                rule.setApplyBeforeHours(rs.getInt("ApplyBeforeHours"));
                rule.setRefundPercentage(rs.getDouble("RefundPercentage"));
                rule.setApplyToPassengerTypeId((Integer) rs.getObject("ApplyToPassengerTypeId"));
                rule.setApplyToCarriageTypeId((Integer) rs.getObject("ApplyToCarriageTypeId"));
                rule.setNote(rs.getString("Note"));
                rule.setActive(rs.getBoolean("IsActive"));
                list.add(rule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int add(RefundRule rule) {
        String sql = "INSERT INTO RefundRules " +
                "(RuleName, ApplyBeforeHours, RefundPercentage, ApplyToPassengerTypeId, ApplyToCarriageTypeId, Note, IsActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, rule.getRuleName());
            ps.setInt(2, rule.getApplyBeforeHours());
            ps.setDouble(3, rule.getRefundPercentage());

            if (rule.getApplyToPassengerTypeId() != null)
                ps.setInt(4, rule.getApplyToPassengerTypeId());
            else
                ps.setNull(4, Types.INTEGER);

            if (rule.getApplyToCarriageTypeId() != null)
                ps.setInt(5, rule.getApplyToCarriageTypeId());
            else
                ps.setNull(5, Types.INTEGER);

            ps.setString(6, rule.getNote());
            ps.setBoolean(7, rule.isActive());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void update(RefundRule rule) {
        String sql = "UPDATE RefundRules SET RuleName=?, ApplyBeforeHours=?, RefundPercentage=?, " +
                "ApplyToPassengerTypeId=?, ApplyToCarriageTypeId=?, Note=?, IsActive=? WHERE Id=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rule.getRuleName());
            ps.setInt(2, rule.getApplyBeforeHours());
            ps.setDouble(3, rule.getRefundPercentage());

            if (rule.getApplyToPassengerTypeId() != null)
                ps.setInt(4, rule.getApplyToPassengerTypeId());
            else
                ps.setNull(4, Types.INTEGER);

            if (rule.getApplyToCarriageTypeId() != null)
                ps.setInt(5, rule.getApplyToCarriageTypeId());
            else
                ps.setNull(5, Types.INTEGER);

            ps.setString(6, rule.getNote());
            ps.setBoolean(7, rule.isActive());
            ps.setInt(8, rule.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivate(int ruleId) {
        String sql = "UPDATE RefundRules SET IsActive = 0 WHERE Id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ruleId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
