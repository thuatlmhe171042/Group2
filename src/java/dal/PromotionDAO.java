package dal;

import models.Promotion;
import java.sql.*;
import java.util.*;

public class PromotionDAO {

    public List<Promotion> getAll() {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotions WHERE IsDeleted=0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Promotion(
                    rs.getInt("Id"),
                    rs.getString("PromotionCode"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("DiscountType"),
                    rs.getDouble("DiscountValue"),
                    rs.getDate("StartDate").toString(),
                    rs.getDate("EndDate").toString(),
                    rs.getInt("UsageLimit"),
                    rs.getInt("CurrentUsageCount"),
                    rs.getString("Status"),
                    rs.getTimestamp("CreatedAt").toString(),
                    rs.getInt("CreatedBy"),
                    rs.getBoolean("IsDeleted")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addPromotion(Promotion p) {
        String sql = "INSERT INTO Promotions " +
                "(PromotionCode, Name, Description, DiscountType, DiscountValue, StartDate, EndDate, UsageLimit, CurrentUsageCount, Status, CreatedAt, CreatedBy, IsDeleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getPromotionCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getDiscountType());
            ps.setDouble(5, p.getDiscountValue());
            ps.setDate(6, java.sql.Date.valueOf(p.getStartDate()));
            ps.setDate(7, java.sql.Date.valueOf(p.getEndDate()));
            ps.setInt(8, p.getUsageLimit());
            ps.setInt(9, p.getCurrentUsageCount());
            ps.setString(10, p.getStatus());
            ps.setTimestamp(11, Timestamp.valueOf(p.getCreatedAt()));
            ps.setInt(12, p.getCreatedBy());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updatePromotion(Promotion p) {
        String sql = "UPDATE Promotions SET " +
                "PromotionCode=?, Name=?, Description=?, DiscountType=?, DiscountValue=?, StartDate=?, EndDate=?, UsageLimit=?, CurrentUsageCount=?, Status=?, CreatedAt=?, CreatedBy=? " +
                "WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getPromotionCode());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getDiscountType());
            ps.setDouble(5, p.getDiscountValue());
            ps.setDate(6, java.sql.Date.valueOf(p.getStartDate()));
            ps.setDate(7, java.sql.Date.valueOf(p.getEndDate()));
            ps.setInt(8, p.getUsageLimit());
            ps.setInt(9, p.getCurrentUsageCount());
            ps.setString(10, p.getStatus());
            ps.setTimestamp(11, Timestamp.valueOf(p.getCreatedAt()));
            ps.setInt(12, p.getCreatedBy());
            ps.setInt(13, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePromotion(int id) {
        String sql = "UPDATE Promotions SET IsDeleted=1 WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double checkPromotion(String code, double originalAmount) {
        double discount = 0;
        String sql = "SELECT DiscountType, DiscountValue FROM Promotions " +
                     "WHERE PromotionCode = ? AND IsDeleted = 0 AND GETDATE() BETWEEN StartDate AND EndDate " +
                     "AND UsageLimit > CurrentUsageCount";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("DiscountType");
                double value = rs.getDouble("DiscountValue");
                if ("percent".equalsIgnoreCase(type)) {
                    discount = originalAmount * value / 100.0;
                } else if ("fixed".equalsIgnoreCase(type)) {
                    discount = value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discount;
    }

    public Promotion getByCode(String code) {
        String sql = "SELECT * FROM Promotions WHERE PromotionCode = ? AND IsDeleted = 0 AND UsageLimit > CurrentUsageCount";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promotion promo = new Promotion();
                promo.setId(rs.getInt("Id"));
                promo.setPromotionCode(rs.getString("PromotionCode"));
                promo.setDiscountType(rs.getString("DiscountType"));
                promo.setDiscountValue(rs.getDouble("DiscountValue"));
                promo.setUsageLimit(rs.getInt("UsageLimit"));
                promo.setCurrentUsageCount(rs.getInt("CurrentUsageCount"));
                return promo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void incrementUsageCount(int promotionId) {
        String sql = "UPDATE Promotions SET CurrentUsageCount = CurrentUsageCount + 1 WHERE Id = ? AND CurrentUsageCount < UsageLimit";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
