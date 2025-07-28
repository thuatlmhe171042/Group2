package dal;

import models.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {
    public Train getTrainById(int trainId) {
        String sql = "SELECT * FROM Trains WHERE Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Train t = new Train();
                    t.setId(rs.getInt("Id"));
                    t.setTrainCode(rs.getString("TrainCode"));
                    t.setTrainName(rs.getString("TrainName"));
                    t.setDescription(rs.getString("Description"));
                    t.setCreatedAt(rs.getString("CreatedAt"));
                    t.setUpdatedAt(rs.getString("UpdatedAt"));
                    t.setIsDeleted(rs.getBoolean("IsDeleted"));
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Train> getAllTrains(boolean includeDeleted) {
    List<Train> list = new ArrayList<>();
    String sql = "SELECT * FROM Trains" + (includeDeleted ? "" : " WHERE IsDeleted = 0");
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(new Train(
                rs.getInt("Id"),
                rs.getString("TrainCode"),
                rs.getString("TrainName"),
                rs.getString("Description"),
                rs.getString("CreatedAt"),
                rs.getString("UpdatedAt"),
                rs.getBoolean("IsDeleted")
            ));
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}
    
    public List<Train> getAll() {
    List<Train> list = new ArrayList<>();
    String sql = "SELECT * FROM Trains WHERE IsDeleted=0";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(new Train(
                rs.getInt("Id"),
                rs.getString("TrainCode"),
                rs.getString("TrainName"),
                rs.getString("Description"),
                rs.getString("CreatedAt"),
                rs.getString("UpdatedAt"),
                rs.getBoolean("IsDeleted")
            ));
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}

    


public Train getById(int id) {
    String sql = "SELECT * FROM Trains WHERE Id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Train(
                    rs.getInt("Id"),
                    rs.getString("TrainCode"),
                    rs.getString("TrainName"),
                    rs.getString("Description"),
                    rs.getString("CreatedAt"),
                    rs.getString("UpdatedAt"),
                    rs.getBoolean("IsDeleted")
                );
            }
        }
    } catch (Exception e) { e.printStackTrace(); }
    return null;
}

public int addTrain(Train t) {
    String sql = "INSERT INTO Trains (TrainCode, TrainName, Description, IsDeleted) VALUES (?, ?, ?, 0)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, t.getTrainCode());
        ps.setString(2, t.getTrainName());
        ps.setString(3, t.getDescription());
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return -1;
}

public void updateTrain(Train t) {
    String sql = "UPDATE Trains SET TrainCode=?, TrainName=?, Description=?, UpdatedAt=GETDATE() WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, t.getTrainCode());
        ps.setString(2, t.getTrainName());
        ps.setString(3, t.getDescription());
        ps.setInt(4, t.getId());
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}

public void toggleStatus(int id, boolean isDeleted) {
    String sql = "UPDATE Trains SET IsDeleted=?, UpdatedAt=GETDATE() WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setBoolean(1, isDeleted);
        ps.setInt(2, id);
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}




}
