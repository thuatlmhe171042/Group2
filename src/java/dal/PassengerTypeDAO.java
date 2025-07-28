package dal;

import models.PassengerTypeRuleRow;
import java.sql.*;
import java.util.*;

public class PassengerTypeDAO {

    public List<PassengerTypeRuleRow> getAllForManagement() {
        List<PassengerTypeRuleRow> list = new ArrayList<>();
        String sql = "SELECT pt.Id AS passengerTypeId, pt.TypeName, pt.Description, " +
                "r.Id AS ruleId, r.DiscountType, r.DiscountValue, r.IsActive " +
                "FROM PassengerTypes pt LEFT JOIN PassengerPricingRules r ON pt.Id = r.PassengerTypeId";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PassengerTypeRuleRow row = new PassengerTypeRuleRow();
                row.setPassengerTypeId(rs.getInt("passengerTypeId"));
                row.setTypeName(rs.getString("TypeName"));
                row.setDescription(rs.getString("Description"));
                row.setRuleId(rs.getObject("ruleId") != null ? rs.getInt("ruleId") : null);
                row.setDiscountType(rs.getString("DiscountType"));
                row.setDiscountValue(rs.getObject("DiscountValue") != null ? rs.getDouble("DiscountValue") : null);
                row.setRuleActive(rs.getObject("IsActive") != null ? rs.getBoolean("IsActive") : null);
                list.add(row);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<PassengerTypeRuleRow> getAllWithRules() {
        List<PassengerTypeRuleRow> list = new ArrayList<>();
         String sql = "SELECT pt.Id, pt.TypeName, pt.Description, " +
                 "r.Id AS RuleId, r.DiscountType, r.DiscountValue, r.IsActive " +
                 "FROM PassengerTypes pt " +
                 "LEFT JOIN PassengerPricingRules r ON pt.Id = r.PassengerTypeId AND r.IsActive=1 " +
                 "WHERE pt.IsDeleted = 0";
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
    
    public int addPassengerType(String typeName, String desc) {
        String sql = "INSERT INTO PassengerTypes (TypeName, Description) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, typeName);
            ps.setString(2, desc);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public void updatePassengerType(int id, String typeName, String desc) {
        String sql = "UPDATE PassengerTypes SET TypeName=?, Description=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, typeName);
            ps.setString(2, desc);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public PassengerTypeRuleRow getRuleByTypeId(int passengerTypeId) {
        String sql = "SELECT pt.Id as passengerTypeId, pt.TypeName, pt.Description, r.Id as ruleId, r.DiscountType, r.DiscountValue, r.IsActive as ruleActive " +
                "FROM PassengerTypes pt LEFT JOIN PassengerPricingRules r ON pt.Id = r.PassengerTypeId WHERE pt.Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, passengerTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PassengerTypeRuleRow row = new PassengerTypeRuleRow();
                    row.setPassengerTypeId(rs.getInt("passengerTypeId"));
                    row.setTypeName(rs.getString("TypeName"));
                    row.setDescription(rs.getString("Description"));
                    row.setRuleId((Integer)rs.getObject("ruleId"));
                    row.setDiscountType(rs.getString("DiscountType"));
                    row.setDiscountValue(rs.getObject("DiscountValue") != null ? rs.getDouble("DiscountValue") : null);
                    row.setRuleActive(rs.getObject("ruleActive") != null ? rs.getBoolean("ruleActive") : null);
                    return row;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    

}
