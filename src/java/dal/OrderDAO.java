package dal;
import models.Order;
import java.sql.*;
import java.util.*;

public class OrderDAO {
    public List<Order> search(String keyword, String status) {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Orders WHERE IsDeleted=0");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (OrderCode LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ?)");
            String kw = "%" + keyword + "%";
            params.add(kw); params.add(kw); params.add(kw);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND Status=?");
            params.add(status);
        }
        sql.append(" ORDER BY OrderTime DESC");
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Order(
                        rs.getInt("Id"),
                        rs.getString("OrderCode"),
                        (Integer)rs.getObject("UserId"), // có thể null
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("OrderTime"),
                        rs.getDouble("OriginalAmount"),
                        rs.getDouble("DiscountAmount"),
                        rs.getString("Status"),
                        rs.getString("UpdatedAt"),
                        rs.getBoolean("IsDeleted")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE Orders SET Status=?, UpdatedAt=GETDATE() WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public int insert(Order order) {
        String sql = "INSERT INTO Orders " +
            "(OrderCode, UserId, Email, PhoneNumber, OrderTime, OriginalAmount, DiscountAmount, Status, UpdatedAt, IsDeleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), 0)";
        int generatedId = -1;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getOrderCode());
            if (order.getUserId() != null)
                ps.setInt(2, order.getUserId());
            else
                ps.setNull(2, Types.INTEGER);
            ps.setString(3, order.getEmail());
            ps.setString(4, order.getPhoneNumber());
            ps.setString(5, order.getOrderTime());
            ps.setDouble(6, order.getOriginalAmount());
            ps.setDouble(7, order.getDiscountAmount());
            ps.setString(8, order.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return generatedId;
    }
    
    public Order getByOrderCode(String orderCode) {
    String sql = "SELECT * FROM Orders WHERE OrderCode = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, orderCode);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Order(
                    rs.getInt("Id"),
                    rs.getString("OrderCode"),
                    (Integer)rs.getObject("UserId"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("OrderTime"),
                    rs.getDouble("OriginalAmount"),
                    rs.getDouble("DiscountAmount"),
                    rs.getString("Status"),
                    rs.getString("UpdatedAt"),
                    rs.getBoolean("IsDeleted")
                );
            }
        }
    } catch (Exception e) { e.printStackTrace(); }
    return null;
}


    public Order findByCodeEmailPhone(String orderCode, String email, String phone) {
    String sql = "SELECT * FROM Orders WHERE OrderCode = ? AND Email = ? AND PhoneNumber = ? AND IsDeleted = 0";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, orderCode);
        ps.setString(2, email);
        ps.setString(3, phone);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Order(
                    rs.getInt("Id"),
                    rs.getString("OrderCode"),
                    (Integer)rs.getObject("UserId"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("OrderTime"),
                    rs.getDouble("OriginalAmount"),
                    rs.getDouble("DiscountAmount"),
                    rs.getString("Status"),
                    rs.getString("UpdatedAt"),
                    rs.getBoolean("IsDeleted")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


    
    public void updateOrderStatusByOrderCode(String orderCode, String status) {
    String sql = "UPDATE Orders SET Status=?, UpdatedAt=GETDATE() WHERE OrderCode=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setString(2, orderCode);
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}

}
