/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.User;
import Utils.PasswordUtils;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {
    
    public boolean register(User user) {
        // Assuming CreatedAt column has a DEFAULT value (e.g., CURRENT_TIMESTAMP) in the database
        String sql = "INSERT INTO Users (Name, Email, Password, Phone, Role) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // Password should be hashed
            ps.setString(4, user.getPhone());
            ps.setString(5, "customer"); // Default role to 'customer'
            
            boolean result = ps.executeUpdate() > 0;
            if (!result) {
                System.out.println("UserDAO.register: Failed to insert user. executeUpdate() did not return > 0.");
            }
            return result;
        } catch (SQLException e) {
            System.out.println("UserDAO.register: SQLException during user registration for email: " + user.getEmail());
            e.printStackTrace();
            return false;
        }
    }

    // Get user by email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setRole(rs.getString("Role"));
                Timestamp timestamp = rs.getTimestamp("CreatedAt");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error getting user by email: " + e.getMessage());
        }
        return null;
    }

    // Check if email exists
    public boolean checkEmailExists(String email) {
        String sql = "SELECT Email FROM Users WHERE Email = ?";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("UserDAO.checkEmailExists: SQLException while checking email: " + email);
            e.printStackTrace();
            // To make it clear that an error happened and not that the email definitively doesn't exist,
            // one might throw an exception here. For now, returning false but logging clearly.
            System.out.println("UserDAO.checkEmailExists: Returning false due to SQLException. This might lead to issues if email actually exists.");
            return false;
        }
    }

    // Update user password
    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE Email = ?";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidResetToken(String token) {
        String sql = "SELECT expiry_time FROM password_reset_tokens " +
                    "WHERE token = ? AND used = 0 AND expiry_time > GETDATE()";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking reset token: " + e.getMessage());
            return false;
        }
    }
    
    // Get email associated with reset token
    public String getEmailFromResetToken(String token) {
        String sql = "SELECT u.Email FROM Users u " +
                    "JOIN password_reset_tokens t ON u.Email = t.email " +
                    "WHERE t.token = ? AND t.used = 0 AND t.expiry_time > GETDATE()";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Email");
            }
        } catch (SQLException e) {
            System.out.println("Error getting email from token: " + e.getMessage());
        }
        return null;
    }
    
    // Create password reset token
    public String createResetToken(String email) {
        String token = generateToken();
        String sql = "INSERT INTO password_reset_tokens (email, token, expiry_time, used) " +
                    "VALUES (?, ?, DATEADD(hour, 1, GETDATE()), 0)";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, token);
            
            if (ps.executeUpdate() > 0) {
                return token;
            }
        } catch (SQLException e) {
            System.out.println("Error creating reset token: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // Mark reset token as used
    public void invalidateResetToken(String token) {
        String sql = "UPDATE password_reset_tokens SET used = 1 WHERE token = ?";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error invalidating reset token: " + e.getMessage());
        }
    }
    
    // Generate random token
    private String generateToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM Users WHERE Email = ? AND Role = 'customer'";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");

                // First, try the secure way (for new or updated users)
                if (PasswordUtils.verifyPassword(password, storedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("Id"));
                    user.setName(rs.getString("Name"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(storedPassword);
                    user.setPhone(rs.getString("Phone"));
                    user.setRole(rs.getString("Role"));
                    Timestamp createdAtTimestamp = rs.getTimestamp("CreatedAt");
                    if (createdAtTimestamp != null) {
                        user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                    }
                    return user;
                }

                // Fallback for old, plain-text passwords
                if (storedPassword.equals(password)) {
                    // Password is correct, but it's an old one. Let's update it.
                    String newHashedPassword = PasswordUtils.hashPassword(password);
                    updatePassword(email, newHashedPassword);

                    User user = new User();
                    user.setId(rs.getInt("Id"));
                    user.setName(rs.getString("Name"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(newHashedPassword); // Return with the new hash
                    user.setPhone(rs.getString("Phone"));
                    user.setRole(rs.getString("Role"));
                    Timestamp createdAtTimestamp = rs.getTimestamp("CreatedAt");
                    if (createdAtTimestamp != null) {
                        user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in customer login: " + e.getMessage());
        }
        return null;
    }

    // Admin/Staff login check
    public User checkAdminLogin(String email, String password, String role) {
        String sql = "SELECT * FROM Users WHERE Email = ? AND Role = ?";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                
                // First, try the secure way
                if (PasswordUtils.verifyPassword(password, storedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("Id"));
                    user.setName(rs.getString("Name"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(storedPassword);
                    user.setPhone(rs.getString("Phone"));
                    user.setRole(rs.getString("Role"));
                    Timestamp createdAtTimestamp = rs.getTimestamp("CreatedAt");
                    if (createdAtTimestamp != null) {
                        user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                    }
                    return user;
                }

                // Fallback for old, plain-text passwords
                if (storedPassword.equals(password)) {
                    // Password is correct, let's update it to the new hash
                    String newHashedPassword = PasswordUtils.hashPassword(password);
                    updatePassword(email, newHashedPassword);

                    User user = new User();
                    user.setId(rs.getInt("Id"));
                    user.setName(rs.getString("Name"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(newHashedPassword);
                    user.setPhone(rs.getString("Phone"));
                    user.setRole(rs.getString("Role"));
                    Timestamp createdAtTimestamp = rs.getTimestamp("CreatedAt");
                    if (createdAtTimestamp != null) {
                        user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in admin/staff login: " + e.getMessage());
        }
        return null;
    }
}