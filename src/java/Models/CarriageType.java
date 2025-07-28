package models;

public class CarriageType {
    private int id;
    private String typeName;
    private String description;
    private Double basePricePerKm;

    public CarriageType() {}

    public CarriageType(int id, String typeName, String description, Double basePricePerKm) {
        this.id = id;
        this.typeName = typeName;
        this.description = description;
        this.basePricePerKm = basePricePerKm;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getBasePricePerKm() { return basePricePerKm; }
    public void setBasePricePerKm(Double basePricePerKm) { this.basePricePerKm = basePricePerKm; }
}
