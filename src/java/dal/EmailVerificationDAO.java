package dal;

import models.EmailVerification;
import java.sql.*;
import java.util.Date;

public class EmailVerificationDAO {
    // Thêm bản ghi OTP
    public boolean insertOtp(String email, String otp, String actionType, Date expiresAt) {
        String sql = "INSERT INTO EmailVerifications (Email, OTPCode, ActionType, CreatedAt, ExpiresAt, IsUsed) VALUES (?, ?, ?, GETDATE(), ?, 0)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            ps.setString(3, actionType);
            ps.setTimestamp(4, new java.sql.Timestamp(expiresAt.getTime()));
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    }

    // Kiểm tra OTP hợp lệ (email, otp, action, còn hạn, chưa dùng)
    public boolean isValidOtp(String email, String otp, String actionType) {
        String sql = "SELECT TOP 1 Id FROM EmailVerifications WHERE Email = ? AND OTPCode = ? AND ActionType = ? AND IsUsed = 0 AND ExpiresAt > GETDATE()";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            ps.setString(3, actionType);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    }

    // Đánh dấu OTP đã dùng
    public void markOtpUsed(String email, String otp, String actionType) {
        String sql = "UPDATE EmailVerifications SET IsUsed = 1 WHERE Email = ? AND OTPCode = ? AND ActionType = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            ps.setString(3, actionType);
            ps.executeUpdate();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}
