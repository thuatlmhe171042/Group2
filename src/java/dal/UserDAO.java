package dal;

import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // Lấy tổng số staff hiện tại
    public int countActiveStaff() {
        String sql = "SELECT COUNT(*) FROM Users WHERE Role = 'staff' AND IsDeleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách staff đang hoạt động
    public List<User> getAllActiveStaff() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Role = 'staff' AND IsDeleted = 0 ORDER BY CreatedAt DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin staff theo Id
    public User getStaffById(int id) {
        String sql = "SELECT * FROM Users WHERE Id = ? AND Role = 'staff'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm staff mới
    public boolean addStaff(User staff) {
        String sql = "INSERT INTO Users (Name, Email, Password, Phone, Role) VALUES (?, ?, ?, ?, 'staff')";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPassword());
            ps.setString(4, staff.getPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sửa thông tin staff
    public boolean updateStaff(User staff) {
        String sql = "UPDATE Users SET Name = ?, Email = ?, Phone = ?, UpdatedAt = GETDATE() WHERE Id = ? AND Role = 'staff'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPhone());
            ps.setInt(4, staff.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đổi mật khẩu staff (option)
    public boolean updateStaffPassword(int staffId, String newPassword) {
        String sql = "UPDATE Users SET Password = ?, UpdatedAt = GETDATE() WHERE Id = ? AND Role = 'staff'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, staffId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Vô hiệu hóa hoặc kích hoạt staff (xóa logic)
    public boolean setStaffActive(int id, boolean isActive) {
        String sql = "UPDATE Users SET IsDeleted = ? WHERE Id = ? AND Role = 'staff'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, !isActive); // isActive=true thì IsDeleted=false
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm staff theo keyword (tên, email, phone) (phục vụ lọc/search list)
    public List<User> searchStaff(String keyword) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Role = 'staff' AND IsDeleted = 0 " +
                     "AND (Name LIKE ? OR Email LIKE ? OR Phone LIKE ?) ORDER BY CreatedAt DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String q = "%" + keyword + "%";
            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractUserFromResultSet(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm hỗ trợ: lấy User từ ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("Id"));
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setPhone(rs.getString("Phone"));
        user.setRole(rs.getString("Role"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt"));
        user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        user.setDeleted(rs.getBoolean("IsDeleted"));
        return user;
    }
    
    
        
public User login(String email, String password) {
    String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ? AND IsDeleted = 0";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ps.setString(2, password);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public User getUserByEmail(String email) {
    String sql = "SELECT * FROM Users WHERE Email = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return extractUserFromResultSet(rs);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return null;
}

public boolean updatePasswordByEmail(String email, String newPassword) {
    String sql = "UPDATE Users SET Password = ?, UpdatedAt = GETDATE() WHERE Email = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newPassword);
        ps.setString(2, email);
        return ps.executeUpdate() > 0;
    } catch (Exception e) { e.printStackTrace(); }
    return false;
}

// Kiểm tra email đã tồn tại
public boolean isEmailExists(String email) {
    String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
    } catch (Exception e) { e.printStackTrace(); }
    return false;
}
// Cập nhật email
public boolean updateEmail(int userId, String newEmail) {
    String sql = "UPDATE Users SET Email = ?, UpdatedAt = GETDATE() WHERE Id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newEmail);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) { e.printStackTrace(); }
    return false;
}


public boolean updateAdmin(User admin) {
    String sql = "UPDATE Users SET Name = ?, Phone = ?, UpdatedAt = GETDATE() WHERE Id = ? AND Role = 'admin'";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, admin.getName());
        ps.setString(2, admin.getPhone());
        ps.setInt(3, admin.getId());
        return ps.executeUpdate() > 0;
    } catch (Exception e) { e.printStackTrace(); }
    return false;
}


public boolean addCustomer(User user) {
    String sql = "INSERT INTO Users (Name, Email, Password, Phone, Role) VALUES (?, ?, ?, ?, 'customer')";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhone());
        return ps.executeUpdate() > 0;
    } catch (Exception e) { e.printStackTrace(); }
    return false;
}

}
