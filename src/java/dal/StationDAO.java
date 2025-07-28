package dal;

import models.Station;
import java.sql.*;
import java.util.*;
import models.Station;

public class StationDAO {
    public List<Station> getAllStations() {
       List<Station> list = new ArrayList<>();
        String sql = "SELECT * FROM Stations";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Station s = new Station();
                s.setId(rs.getInt("Id"));
                s.setStationCode(rs.getString("StationCode"));
                s.setStationName(rs.getString("StationName"));
                s.setCity(rs.getString("City"));
                s.setAddress(rs.getString("Address"));
                s.setLatitude(rs.getDouble("Latitude"));
                s.setLongitude(rs.getDouble("Longitude"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Station> getAllStations(boolean includeDeleted) {
        List<Station> list = new ArrayList<>();
        String sql = "SELECT * FROM Stations" + (includeDeleted ? "" : " WHERE IsDeleted = 0");
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Station(
                    rs.getInt("Id"),
                    rs.getString("StationCode"),
                    rs.getString("StationName"),
                    rs.getString("City"),
                    rs.getString("Address"),
                    rs.getDouble("Latitude"),
                    rs.getDouble("Longitude"),
                        rs.getBoolean("IsDeleted")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    public Station getStationByDisplayName(String displayName) {
        String sql = "SELECT * FROM Stations WHERE StationName + ' (' + StationCode + ')' = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, displayName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Station s = new Station();
                    s.setId(rs.getInt("Id"));
                    s.setStationCode(rs.getString("StationCode"));
                    s.setStationName(rs.getString("StationName"));
                    s.setCity(rs.getString("City"));
                    s.setAddress(rs.getString("Address"));
                    s.setLatitude(rs.getDouble("Latitude"));
                    s.setLongitude(rs.getDouble("Longitude"));
                    return s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Station getById(int id) {
        String sql = "SELECT * FROM Stations WHERE Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Station(
                        rs.getInt("Id"),
                        rs.getString("StationCode"),
                        rs.getString("StationName"),
                        rs.getString("City"),
                        rs.getString("Address"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"),
                        rs.getBoolean("IsDeleted")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    
    
    public int addStation(Station s) {
        String sql = "INSERT INTO Stations (StationCode, StationName, City, Address, Latitude, Longitude, IsDeleted) VALUES (?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getStationCode());
            ps.setString(2, s.getStationName());
            ps.setString(3, s.getCity());
            ps.setString(4, s.getAddress());
            ps.setDouble(5, s.getLatitude());
            ps.setDouble(6, s.getLongitude());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }
    
    
    public void updateStation(Station s) {
        String sql = "UPDATE Stations SET StationCode=?, StationName=?, City=?, Address=?, Latitude=?, Longitude=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStationCode());
            ps.setString(2, s.getStationName());
            ps.setString(3, s.getCity());
            ps.setString(4, s.getAddress());
            ps.setDouble(5, s.getLatitude());
            ps.setDouble(6, s.getLongitude());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void toggleStatus(int id, boolean isDeleted) {
        String sql = "UPDATE Stations SET IsDeleted=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isDeleted);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
