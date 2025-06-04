/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {
    
    // Phương thức đăng nhập cho user thường
    public User login(String email, String password) {
        try {
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ? AND role = 'user'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error in login: " + e.getMessage());
        }
        return null;
    }

    // Phương thức đăng nhập cho admin/staff
    public User checkAdminLogin(String email, String password, String role) {
        try {
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ? AND role = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error in checkAdminLogin: " + e.getMessage());
        }
        return null;
    }

    // Phương thức đăng ký user mới
    public boolean register(User user) {
        try {
            // Kiểm tra email đã tồn tại chưa
            if (checkEmailExists(user.getEmail())) {
                return false;
            }
            
            String sql = "INSERT INTO Users (name, email, password, phone, role) VALUES (?, ?, ?, ?, 'user')";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error in register: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra email tồn tại
    public boolean checkEmailExists(String email) {
        try {
            String sql = "SELECT 1 FROM Users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error in checkEmailExists: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra email tồn tại với role cụ thể
    public boolean checkEmailExistsWithRole(String email, String role) {
        try {
            String sql = "SELECT 1 FROM Users WHERE email = ? AND role = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error in checkEmailExistsWithRole: " + e.getMessage());
            return false;
        }
    }

    // Lấy thông tin user bằng email
    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error in getUserByEmail: " + e.getMessage());
        }
        return null;
    }

    // Cập nhật mật khẩu
    public boolean updatePassword(String email, String newPassword) {
        try {
            String sql = "UPDATE Users SET password = ? WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error in updatePassword: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra role hợp lệ
    public boolean isValidRole(String role) {
        return role != null && (role.equals("admin") || role.equals("staff"));
    }

    // Lấy danh sách tất cả users (cho admin quản lý)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllUsers: " + e.getMessage());
        }
        return users;
    }
}