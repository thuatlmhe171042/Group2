package dal;

import models.TrainSchedule;
import java.sql.*;
import java.util.*;

public class TrainScheduleDAO {
     public List<TrainSchedule> searchSchedules(int fromStationId, int toStationId, String departDate) {
        List<TrainSchedule> list = new ArrayList<>();
        String sql =
            "SELECT DISTINCT ts.* " +
            "FROM TrainSchedules ts " +
            "JOIN ScheduleStops s1 ON ts.Id = s1.ScheduleId " +
            "JOIN ScheduleStops s2 ON ts.Id = s2.ScheduleId " +
            "WHERE s1.StationId = ? AND s2.StationId = ? " +
            "AND s1.StopSequence < s2.StopSequence " +
            "AND CONVERT(DATE, ts.DepartureTime) = ? " +
            "AND ts.Status = 'available' AND ts.IsDeleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fromStationId);
            ps.setInt(2, toStationId);
            ps.setString(3, departDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TrainSchedule ts = new TrainSchedule();
                    ts.setId(rs.getInt("Id"));
                    ts.setTrainId(rs.getInt("TrainId"));
                    ts.setDepartureStationId(rs.getInt("DepartureStationId"));
                    ts.setArrivalStationId(rs.getInt("ArrivalStationId"));
                    ts.setDepartureTime(rs.getString("DepartureTime"));
                    ts.setArrivalTime(rs.getString("ArrivalTime"));
                    ts.setStatus(rs.getString("Status"));
                    ts.setIsDeleted(rs.getInt("IsDeleted"));
                    list.add(ts);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public TrainSchedule getScheduleById(int scheduleId) {
        String sql = "SELECT * FROM TrainSchedules WHERE Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TrainSchedule t = new TrainSchedule();
                    t.setId(rs.getInt("Id"));
                    t.setTrainId(rs.getInt("TrainId"));
                    t.setDepartureStationId(rs.getInt("DepartureStationId"));
                    t.setArrivalStationId(rs.getInt("ArrivalStationId"));
                    t.setDepartureTime(rs.getString("DepartureTime"));
                    t.setArrivalTime(rs.getString("ArrivalTime"));
                    t.setStatus(rs.getString("Status"));
                    t.setIsDeleted(rs.getInt("IsDeleted"));
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<TrainSchedule> getAll() {
        List<TrainSchedule> list = new ArrayList<>();
        String sql = "SELECT * FROM TrainSchedules WHERE IsDeleted = 0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TrainSchedule sch = new TrainSchedule(
                    rs.getInt("Id"),
                    rs.getInt("TrainId"),
                    rs.getInt("DepartureStationId"),
                    rs.getInt("ArrivalStationId"),
                    rs.getString("DepartureTime"),
                    rs.getString("ArrivalTime"),
                    rs.getString("Status"),
                    rs.getInt("IsDeleted")
                );
                list.add(sch);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
     public List<TrainSchedule> getByTrainId(int trainId) {
        List<TrainSchedule> list = new ArrayList<>();
        String sql = "SELECT * FROM TrainSchedules WHERE TrainId=? AND IsDeleted=0";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TrainSchedule(
                        rs.getInt("Id"),
                        rs.getInt("TrainId"),
                        rs.getInt("DepartureStationId"),
                        rs.getInt("ArrivalStationId"),
                        rs.getString("DepartureTime"),
                        rs.getString("ArrivalTime"),
                        rs.getString("Status"),
                        rs.getInt("IsDeleted")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

     
      public int addSchedule(TrainSchedule sch) {
        String sql = "INSERT INTO TrainSchedules (TrainId, DepartureStationId, ArrivalStationId, DepartureTime, ArrivalTime, Status, IsDeleted) VALUES (?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, sch.getTrainId());
            ps.setInt(2, sch.getDepartureStationId());
            ps.setInt(3, sch.getArrivalStationId());
            ps.setString(4, sch.getDepartureTime());
            ps.setString(5, sch.getArrivalTime());
            ps.setString(6, sch.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }
    public void updateSchedule(TrainSchedule sch) {
        String sql = "UPDATE TrainSchedules SET DepartureStationId=?, ArrivalStationId=?, DepartureTime=?, ArrivalTime=?, Status=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sch.getDepartureStationId());
            ps.setInt(2, sch.getArrivalStationId());
            ps.setString(3, sch.getDepartureTime());
            ps.setString(4, sch.getArrivalTime());
            ps.setString(5, sch.getStatus());
            ps.setInt(6, sch.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void deleteSchedule(int id) {
        String sql = "UPDATE TrainSchedules SET IsDeleted=1 WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
