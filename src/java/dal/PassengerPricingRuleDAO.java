package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.PassengerTypeRuleRow;

public class PassengerPricingRuleDAO {
    public int addPricingRule(int passengerTypeId, String discountType, double discountValue) {
        String sql = "INSERT INTO PassengerPricingRules (PassengerTypeId, DiscountType, DiscountValue, IsActive) VALUES (?, ?, ?, 1)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, passengerTypeId);
            ps.setString(2, discountType);
            ps.setDouble(3, discountValue);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public void updatePricingRule(int ruleId, String discountType, double discountValue) {
        String sql = "UPDATE PassengerPricingRules SET DiscountType=?, DiscountValue=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, discountType);
            ps.setDouble(2, discountValue);
            ps.setInt(3, ruleId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void toggleRuleActive(int ruleId, boolean isActive) {
        String sql = "UPDATE PassengerPricingRules SET IsActive=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, ruleId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public List<PassengerTypeRuleRow> getAllWithRules() {
    List<PassengerTypeRuleRow> list = new ArrayList<>();
    String sql = "SELECT pt.Id, pt.TypeName, pt.Description, " +
                 "r.Id AS RuleId, r.DiscountType, r.DiscountValue, r.IsActive " +
                 "FROM PassengerTypes pt " +
                 "LEFT JOIN PassengerPricingRules r ON pt.Id = r.PassengerTypeId AND r.IsActive=1 " +
                 "WHERE pt.IsDeleted = 0 " +
                 "ORDER BY pt.Id";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            PassengerTypeRuleRow row = new PassengerTypeRuleRow();
            row.setPassengerTypeId(rs.getInt("Id"));
            row.setTypeName(rs.getString("TypeName"));
            row.setDescription(rs.getString("Description"));
            row.setRuleId(rs.getObject("RuleId") != null ? rs.getInt("RuleId") : null);
            row.setDiscountType(rs.getString("DiscountType"));
            row.setDiscountValue(rs.getObject("DiscountValue") != null ? rs.getDouble("DiscountValue") : null);
            row.setRuleActive(rs.getObject("IsActive") != null ? rs.getBoolean("IsActive") : null);
            list.add(row);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}

}
