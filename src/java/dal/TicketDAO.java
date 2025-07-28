/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.*;
import models.Ticket;
import models.TicketDetail;


/**
 *
 * @author thuat
 */
public class TicketDAO {
    public Set<Integer> getBookedSeatIds(int scheduleId, int fromStopId, int toStopId) {
        Set<Integer> booked = new HashSet<>();
        String sql = "SELECT SeatId, DepartureStopId, ArrivalStopId FROM Tickets " +
                "WHERE ScheduleId=? AND TicketStatus IN ('paid','pending_payment')";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int seatId = rs.getInt("SeatId");
                    int dep = rs.getInt("DepartureStopId");
                    int arr = rs.getInt("ArrivalStopId");
                    // Giao nhau nếu: (dep < toStopId) && (arr > fromStopId)
                    if ( (dep < toStopId) && (arr > fromStopId) ) {
                        booked.add(seatId);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return booked;
    }
    
    public void updateStatusByOrderCode(String orderCode, String status) {
    String sql = "UPDATE Tickets SET TicketStatus=?, UpdatedAt=GETDATE() " +
                 "WHERE OrderId = (SELECT Id FROM Orders WHERE OrderCode = ?)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setString(2, orderCode);
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}
    
    
    public Ticket getByCode(String ticketCode) {
    String sql = "SELECT * FROM Tickets WHERE TicketCode = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ticketCode);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("Id"));
                t.setTicketCode(rs.getString("TicketCode"));
                t.setPassengerName(rs.getString("PassengerName"));
                t.setPassengerTypeId(rs.getInt("PassengerTypeId"));
                t.setPrice(rs.getDouble("Price"));
                t.setTicketStatus(rs.getString("TicketStatus"));
                return t;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


    
    public List<TicketDetail> getTicketsByOrderId2(int orderId) {
        List<TicketDetail> list = new ArrayList<>();

        String sql = """
            SELECT 
                t.TicketCode, t.PassengerName, t.PassengerIdType, t.PassengerIdNumber,
                t.PassengerDateOfBirth, t.TicketStatus, t.Price,
                pt.TypeName AS PassengerType,
                stFrom.StationName AS FromStation, ssFrom.DepartureTime,
                stTo.StationName AS ToStation, ssTo.ArrivalTime,
                tr.TrainCode,
                c.CarriageNumber, ct.TypeName AS CarriageType,
                s.SeatNumber
            FROM Tickets t
            JOIN PassengerTypes pt ON t.PassengerTypeId = pt.Id
            JOIN ScheduleStops ssFrom ON t.DepartureStopId = ssFrom.Id
            JOIN Stations stFrom ON ssFrom.StationId = stFrom.Id
            JOIN ScheduleStops ssTo ON t.ArrivalStopId = ssTo.Id
            JOIN Stations stTo ON ssTo.StationId = stTo.Id
            JOIN TrainSchedules ts ON t.ScheduleId = ts.Id
            JOIN Trains tr ON ts.TrainId = tr.Id
            JOIN Seats s ON t.SeatId = s.Id
            JOIN Carriages c ON s.CarriageId = c.Id
            JOIN CarriageTypes ct ON c.CarriageTypeId = ct.Id
            WHERE t.OrderId = ? AND t.IsDeleted = 0
            ORDER BY t.Id
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TicketDetail td = new TicketDetail();

                    td.setTicketCode(rs.getString("TicketCode"));
                    td.setPassengerName(rs.getString("PassengerName"));
                    td.setPassengerIdType(rs.getString("PassengerIdType"));
                    td.setPassengerIdNumber(rs.getString("PassengerIdNumber"));
                    td.setPassengerDateOfBirth(rs.getDate("PassengerDateOfBirth"));
                    td.setTicketStatus(rs.getString("TicketStatus"));
                    td.setPrice(rs.getBigDecimal("Price"));
                    td.setPassengerType(rs.getString("PassengerType"));

                    td.setFromStation(rs.getString("FromStation"));
                    td.setDepartureTime(rs.getTimestamp("DepartureTime"));
                    td.setToStation(rs.getString("ToStation"));
                    td.setArrivalTime(rs.getTimestamp("ArrivalTime"));

                    td.setTrainCode(rs.getString("TrainCode"));
                    td.setCarriageNumber(rs.getInt("CarriageNumber"));
                    td.setCarriageType(rs.getString("CarriageType"));
                    td.setSeatNumber(rs.getString("SeatNumber"));

                    list.add(td);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public void updateStatusByCode(String ticketCode, String status) {
        String sql = "UPDATE Tickets SET TicketStatus=?, UpdatedAt=GETDATE() WHERE TicketCode=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, ticketCode);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public List<Ticket> getTicketsByOrderId(int orderId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE OrderId=? AND IsDeleted=0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Ticket(
                        rs.getInt("Id"),
                        rs.getString("TicketCode"),
                        (Integer)rs.getObject("OrderId"),
                        rs.getInt("ScheduleId"),
                        rs.getInt("DepartureStopId"),
                        rs.getInt("ArrivalStopId"),
                        rs.getInt("SeatId"),
                        rs.getInt("PassengerTypeId"),
                        rs.getString("PassengerName"),
                        rs.getString("PassengerIdType"),
                        rs.getString("PassengerIdNumber"),
                        rs.getString("PassengerDateOfBirth"),
                        rs.getString("TicketStatus"),
                        rs.getDouble("Price"),
                        rs.getString("CreatedAt"),
                        rs.getString("UpdatedAt"),
                        rs.getBoolean("IsDeleted")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    
    public void cancelTicket(int ticketId) {
        String sql = "UPDATE Tickets SET TicketStatus='cancelled', UpdatedAt=GETDATE() WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public Timestamp getDepartureTime(String ticketCode) {
    String sql = """
        SELECT ss.DepartureTime
        FROM Tickets t
        JOIN Seats s ON t.SeatId = s.Id
        JOIN Carriages c ON s.CarriageId = c.Id
        JOIN TrainSchedules ts ON t.ScheduleId = ts.Id
        JOIN ScheduleStops ss ON ts.Id = ss.ScheduleId
        WHERE t.TicketCode = ? AND ss.StationId = ts.DepartureStationId
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ticketCode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getTimestamp(1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public int getCarriageTypeId(String ticketCode) {
    String sql = """
        SELECT c.CarriageTypeId
        FROM Tickets t
        JOIN Seats s ON t.SeatId = s.Id
        JOIN Carriages c ON s.CarriageId = c.Id
        WHERE t.TicketCode = ?
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ticketCode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("CarriageTypeId");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}

public String getEmailByTicketCode(String ticketCode) {
    String sql = """
        SELECT o.Email FROM Orders o
        JOIN Tickets t ON o.Id = t.OrderId
        WHERE t.TicketCode = ?
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ticketCode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("Email");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
}

    
    public List<Ticket> getRefundableTicketsByOrderId(int orderId) {
    List<Ticket> list = new ArrayList<>();
    String sql = "SELECT * FROM Tickets WHERE OrderId = ? AND TicketStatus IN ('paid', 'pending-payment')";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("Id"));
                t.setTicketCode(rs.getString("TicketCode"));
                t.setPassengerName(rs.getString("PassengerName"));
                t.setPassengerTypeId(rs.getInt("PassengerTypeId"));
                t.setPrice(rs.getDouble("Price"));
                t.setTicketStatus(rs.getString("TicketStatus"));
                list.add(t);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    
    

    
    public int insert(Ticket ticket) {
    // Validate trước khi insert
    if (ticket.getScheduleId() <= 0 || ticket.getDepartureStopId() <= 0 ||
        ticket.getArrivalStopId() <=0 || ticket.getSeatId() <=0 ||
        ticket.getPassengerTypeId() <=0) {
        System.out.println("Ticket insert fail: thiếu dữ liệu khóa ngoại!");
        return 0;
    }
    String sql = "INSERT INTO Tickets (TicketCode, OrderId, ScheduleId, DepartureStopId, ArrivalStopId, SeatId, PassengerTypeId, PassengerName, PassengerIdType, PassengerIdNumber, PassengerDateOfBirth, TicketStatus, Price, CreatedAt, UpdatedAt, IsDeleted) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, ticket.getTicketCode());
        if (ticket.getOrderId() == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, ticket.getOrderId());
        ps.setInt(3, ticket.getScheduleId());
        ps.setInt(4, ticket.getDepartureStopId());
        ps.setInt(5, ticket.getArrivalStopId());
        ps.setInt(6, ticket.getSeatId());
        ps.setInt(7, ticket.getPassengerTypeId());
        ps.setString(8, ticket.getPassengerName());
        ps.setString(9, ticket.getPassengerIdType());
        ps.setString(10, ticket.getPassengerIdNumber());
        ps.setString(11, ticket.getPassengerDateOfBirth());
        ps.setString(12, ticket.getTicketStatus());
        ps.setDouble(13, ticket.getPrice());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
    } catch (Exception e) {
        System.out.println("Ticket insert exception: scheduleId=" + ticket.getScheduleId()
            + ", seatId=" + ticket.getSeatId() + ", orderId=" + ticket.getOrderId());
        e.printStackTrace();
    }
    return 0;
}

}
