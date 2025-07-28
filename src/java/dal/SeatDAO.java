/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;


import models.Seat;
import java.sql.*;
import java.util.*;

/**
 *
 * @author thuat
 */
public class SeatDAO {
    public List<Seat> getSeatsByCarriageId(int carriageId) {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM Seats WHERE CarriageId = ? AND IsDeleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carriageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat s = new Seat();
                    s.setId(rs.getInt("Id"));
                    s.setCarriageId(rs.getInt("CarriageId"));
                    s.setSeatNumber(rs.getString("SeatNumber"));
                    s.setIsDeleted(rs.getInt("IsDeleted"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int addSeat(Seat s) {
    String sql = "INSERT INTO Seats (CarriageId, SeatNumber, IsDeleted) VALUES (?, ?, 0)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, s.getCarriageId());
        ps.setString(2, s.getSeatNumber());
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return -1;
}
    
    public void updateSeat(Seat s) {
    String sql = "UPDATE Seats SET SeatNumber=? WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, s.getSeatNumber());
        ps.setInt(2, s.getId());
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}
    
    public void deleteSeat(int id) {
    String sql = "UPDATE Seats SET IsDeleted=1 WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}
}
