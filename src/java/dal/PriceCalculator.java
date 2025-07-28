package dal;

import java.sql.*;

public class PriceCalculator {
    // Tính giá dựa vào logic của bạn, có thể sửa lại tùy ý.
    public static double calc(int scheduleId, int fromStopId, int toStopId, int carriageTypeId, int passengerTypeId) {
        double basePrice = 0;
        double discount = 0;

        // 1. Lấy giá cơ bản theo chặng + loại toa
        String sql = "SELECT Price FROM ScheduleSegmentPrices WHERE ScheduleId=? AND DepartureStopId=? AND ArrivalStopId=? AND CarriageTypeId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.setInt(2, fromStopId);
            ps.setInt(3, toStopId);
            ps.setInt(4, carriageTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) basePrice = rs.getDouble("Price");
        } catch (Exception e) { e.printStackTrace(); }

        // 2. Lấy discount theo rule loại hành khách (nếu có)
        String sql2 = "SELECT DiscountType, DiscountValue FROM PassengerPricingRules WHERE ScheduleId=? AND DepartureStopId=? AND ArrivalStopId=? AND PassengerTypeId=? AND Active=1 AND IsDeleted=0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql2)) {
            ps.setInt(1, scheduleId);
            ps.setInt(2, fromStopId);
            ps.setInt(3, toStopId);
            ps.setInt(4, passengerTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("DiscountType");
                double value = rs.getDouble("DiscountValue");
                if ("percent".equalsIgnoreCase(type)) discount = basePrice * value / 100.0;
                else if ("fixed".equalsIgnoreCase(type)) discount = value;
            }
        } catch (Exception e) { e.printStackTrace(); }

        double finalPrice = Math.max(0, Math.round(basePrice - discount));
        return finalPrice;
    }
}
