package dal;

import models.CarriageType;
import java.sql.*;
import java.util.*;

public class CarriageTypeDAO {
    public CarriageType getById(int id) {
        String sql = "SELECT * FROM CarriageTypes WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Double basePricePerKm = rs.getObject("BasePricePerKm") != null ? rs.getDouble("BasePricePerKm") : null;
                    return new CarriageType(
                        rs.getInt("Id"),
                        rs.getString("TypeName"),
                        rs.getString("Description"),
                        basePricePerKm
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    public int addCarriageType(CarriageType t) {
        String sql = "INSERT INTO CarriageTypes (TypeName, Description, BasePricePerKm) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTypeName());
            ps.setString(2, t.getDescription());
            if (t.getBasePricePerKm() == null) {
                ps.setNull(3, Types.DECIMAL);
            } else {
                ps.setDouble(3, t.getBasePricePerKm());
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }
    public void updateCarriageType(CarriageType t) {
        String sql = "UPDATE CarriageTypes SET TypeName=?, Description=?, BasePricePerKm=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getTypeName());
            ps.setString(2, t.getDescription());
            if (t.getBasePricePerKm() == null) {
                ps.setNull(3, Types.DECIMAL);
            } else {
                ps.setDouble(3, t.getBasePricePerKm());
            }
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public Map<Integer, CarriageType> getMapAll() {
    Map<Integer, CarriageType> map = new HashMap<>();
    String sql = "SELECT * FROM CarriageTypes";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            CarriageType ct = new CarriageType(
                rs.getInt("Id"),
                rs.getString("TypeName"),
                rs.getString("Description"),
                rs.getDouble("BasePricePerKm")
            );
            map.put(ct.getId(), ct);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return map;
}
public List<CarriageType> getAll() {
    return new ArrayList<>(getMapAll().values());
}

}
