package dal;

import java.sql.*;

public class RefundDAO {

    public boolean isRefundable(int ticketId) {
        String sql = """
            SELECT TOP 1 r.RefundPercentage
            FROM Tickets t
            JOIN ScheduleStops ss ON t.DepartureStopId = ss.Id
            JOIN Carriages c ON t.SeatId IN (SELECT Id FROM Seats WHERE CarriageId = c.Id)
            JOIN RefundRules r ON 
                (r.ApplyToPassengerTypeId IS NULL OR r.ApplyToPassengerTypeId = t.PassengerTypeId)
                AND (r.ApplyToCarriageTypeId IS NULL OR r.ApplyToCarriageTypeId = c.CarriageTypeId)
            WHERE t.Id = ? AND t.TicketStatus = 'paid'
                AND DATEDIFF(MINUTE, GETDATE(), ss.DepartureTime) >= r.ApplyBeforeHours * 60
                AND r.IsActive = 1
            ORDER BY r.RefundPercentage DESC
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // có ít nhất 1 rule phù hợp
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean refundTicket(int ticketId, int staffUserId, double refundAmount, String reason) {
    String updateTicketSql = "UPDATE Tickets SET TicketStatus = 'refunded', UpdatedAt = GETDATE() WHERE Id = ?";
    String insertRefundSql = "INSERT INTO Refunds (TicketId, ProcessedBy, RefundTime, RefundAmount, Reason) VALUES (?, ?, GETDATE(), ?, ?)";

    try (Connection conn = DBContext.getConnection()) {
        conn.setAutoCommit(false);
        try (
            PreparedStatement ps1 = conn.prepareStatement(updateTicketSql);
            PreparedStatement ps2 = conn.prepareStatement(insertRefundSql)
        ) {
            ps1.setInt(1, ticketId);
            ps1.executeUpdate();

            ps2.setInt(1, ticketId);
            if (staffUserId == -1) {
                ps2.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps2.setInt(2, staffUserId);
            }
            ps2.setDouble(3, refundAmount);
            ps2.setString(4, reason);
            ps2.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
