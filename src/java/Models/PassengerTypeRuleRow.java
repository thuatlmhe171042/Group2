package models;

public class PassengerTypeRuleRow {
    private int passengerTypeId;
    private String typeName;
    private String description;
    private Integer ruleId; // null nếu chưa có rule
    private String discountType; // "percent"/"fixed"
    private Double discountValue; // null nếu chưa có rule
    private Boolean ruleActive;   // null nếu chưa có rule

    public int getPassengerTypeId() { return passengerTypeId; }
    public void setPassengerTypeId(int id) { this.passengerTypeId = id; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String t) { this.typeName = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public Integer getRuleId() { return ruleId; }
    public void setRuleId(Integer ruleId) { this.ruleId = ruleId; }
    public String getDiscountType() { return discountType; }
    public void setDiscountType(String dt) { this.discountType = dt; }
    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double dv) { this.discountValue = dv; }
    public Boolean getRuleActive() { return ruleActive; }
    public void setRuleActive(Boolean act) { this.ruleActive = act; }
}
