package dal;

import models.Carriage;
import java.sql.*;
import java.util.*;

public class CarriageDAO {

    
    public List<Carriage> getCarriagesByTrainId(int trainId) {
    List<Carriage> list = new ArrayList<>();
    String sql = "SELECT * FROM Carriages WHERE TrainId=? AND IsDeleted=0";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, trainId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Carriage(
                    rs.getInt("Id"),
                    rs.getInt("TrainId"),
                    rs.getInt("CarriageTypeId"),
                    rs.getInt("CarriageNumber"),
                    rs.getBoolean("IsDeleted")
                ));
            }
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}

public int addCarriage(Carriage c) {
    String sql = "INSERT INTO Carriages (TrainId, CarriageTypeId, CarriageNumber) VALUES (?, ?, ?)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, c.getTrainId());
        ps.setInt(2, c.getCarriageTypeId());
        ps.setInt(3, c.getCarriageNumber());
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) return rs.getInt(1);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return -1;
}

public void updateCarriage(Carriage c) {
    String sql = "UPDATE Carriages SET CarriageTypeId=?, CarriageNumber=? WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, c.getCarriageTypeId());
        ps.setInt(2, c.getCarriageNumber());
        ps.setInt(3, c.getId());
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}

public void deleteCarriage(int id) {
    String sql = "UPDATE Carriages SET IsDeleted=1 WHERE Id=?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
}

}
