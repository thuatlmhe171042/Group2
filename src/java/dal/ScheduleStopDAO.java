package dal;

import models.ScheduleStop;
import java.sql.*;
import java.util.*;

public class ScheduleStopDAO {
    public List<ScheduleStop> getStopsByScheduleId(int scheduleId) {
        List<ScheduleStop> list = new ArrayList<>();
        String sql = "SELECT * FROM ScheduleStops WHERE ScheduleId = ? ORDER BY StopSequence ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ScheduleStop s = new ScheduleStop();
                    s.setId(rs.getInt("Id"));
                    s.setScheduleId(rs.getInt("ScheduleId"));
                    s.setStationId(rs.getInt("StationId"));
                    s.setStopSequence(rs.getInt("StopSequence"));
                    s.setArrivalTime(rs.getString("ArrivalTime"));
                    s.setDepartureTime(rs.getString("DepartureTime"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int addStop(ScheduleStop stop) {
    String sql = "INSERT INTO ScheduleStops (ScheduleId, StationId, StopSequence, ArrivalTime, DepartureTime) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, stop.getScheduleId());
        ps.setInt(2, stop.getStationId());
        ps.setInt(3, stop.getStopSequence());
        // arrivalTime
        if (stop.getArrivalTime() != null && !stop.getArrivalTime().isEmpty())
            ps.setString(4, stop.getArrivalTime());
        else
            ps.setNull(4, java.sql.Types.VARCHAR);
        // departureTime
        if (stop.getDepartureTime() != null && !stop.getDepartureTime().isEmpty())
            ps.setString(5, stop.getDepartureTime());
        else
            ps.setNull(5, java.sql.Types.VARCHAR);
        int affected = ps.executeUpdate();
        if (affected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
    } catch (Exception ex) { ex.printStackTrace(); }
    return 0;
}

    public void updateStop(ScheduleStop stop) {
        String sql = "UPDATE ScheduleStops SET StationId=?, StopSequence=?, ArrivalTime=?, DepartureTime=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stop.getStationId());
            ps.setInt(2, stop.getStopSequence());
            ps.setString(3, stop.getArrivalTime());
            ps.setString(4, stop.getDepartureTime());
            ps.setInt(5, stop.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void deleteStop(int id) {
        String sql = "UPDATE ScheduleStops SET IsDeleted=1 WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
