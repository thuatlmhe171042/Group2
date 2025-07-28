package dal;

import models.ScheduleSegmentPrice;
import java.sql.*;
import java.util.*;

public class ScheduleSegmentPriceDAO {
    public List<ScheduleSegmentPrice> getPrices(int scheduleId, int departureStopId, int arrivalStopId) {
        List<ScheduleSegmentPrice> list = new ArrayList<>();
        String sql = "SELECT * FROM ScheduleSegmentPrices WHERE ScheduleId = ? AND DepartureStopId = ? AND ArrivalStopId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.setInt(2, departureStopId);
            ps.setInt(3, arrivalStopId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ScheduleSegmentPrice p = new ScheduleSegmentPrice();
                    p.setId(rs.getInt("Id"));
                    p.setScheduleId(rs.getInt("ScheduleId"));
                    p.setDepartureStopId(rs.getInt("DepartureStopId"));
                    p.setArrivalStopId(rs.getInt("ArrivalStopId"));
                    p.setCarriageTypeId(rs.getInt("CarriageTypeId"));
                    p.setPrice(rs.getDouble("Price"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Double getPrice(int scheduleId, int fromStopId, int toStopId, int carriageTypeId) {
        String sql = "SELECT Price FROM ScheduleSegmentPrices WHERE ScheduleId=? AND DepartureStopId=? AND ArrivalStopId=? AND CarriageTypeId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.setInt(2, fromStopId);
            ps.setInt(3, toStopId);
            ps.setInt(4, carriageTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("Price");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    
    
    public void insertOrUpdatePrice(int scheduleId, int fromStopId, int toStopId, int carriageTypeId, double price) {
        String checkSql = "SELECT Id FROM ScheduleSegmentPrices WHERE ScheduleId=? AND DepartureStopId=? AND ArrivalStopId=? AND CarriageTypeId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement check = conn.prepareStatement(checkSql)) {
            check.setInt(1, scheduleId);
            check.setInt(2, fromStopId);
            check.setInt(3, toStopId);
            check.setInt(4, carriageTypeId);
            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    // Update
                    String updateSql = "UPDATE ScheduleSegmentPrices SET Price=? WHERE Id=?";
                    try (PreparedStatement upd = conn.prepareStatement(updateSql)) {
                        upd.setDouble(1, price);
                        upd.setInt(2, rs.getInt("Id"));
                        upd.executeUpdate();
                    }
                } else {
                    // Insert
                    String insertSql = "INSERT INTO ScheduleSegmentPrices (ScheduleId, DepartureStopId, ArrivalStopId, CarriageTypeId, Price) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                        ins.setInt(1, scheduleId);
                        ins.setInt(2, fromStopId);
                        ins.setInt(3, toStopId);
                        ins.setInt(4, carriageTypeId);
                        ins.setDouble(5, price);
                        ins.executeUpdate();
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}

