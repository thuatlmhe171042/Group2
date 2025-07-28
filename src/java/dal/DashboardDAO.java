package dal;

import java.sql.*;
import java.math.BigDecimal;

public class DashboardDAO {

    public int countTrains() {
        String sql = "SELECT COUNT(*) FROM Trains WHERE IsDeleted = 0";
        return getCount(sql);
    }

    public int countStations() {
        String sql = "SELECT COUNT(*) FROM Stations";
        return getCount(sql);
    }

    public int countCarriageTypes() {
        String sql = "SELECT COUNT(*) FROM CarriageTypes";
        return getCount(sql);
    }

    public int countPassengerTypes() {
        String sql = "SELECT COUNT(*) FROM PassengerTypes";
        return getCount(sql);
    }

    public int countTicketsToday() {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE CAST(CreatedAt AS DATE) = CAST(GETDATE() AS DATE) AND IsDeleted = 0";
        return getCount(sql);
    }

    public BigDecimal getRevenueToday() {
        String sql = "SELECT SUM(Price) FROM Tickets WHERE TicketStatus = 'paid' AND CAST(CreatedAt AS DATE) = CAST(GETDATE() AS DATE) AND IsDeleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    private int getCount(String sql) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
