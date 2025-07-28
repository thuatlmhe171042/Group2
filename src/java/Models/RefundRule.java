package models;

public class RefundRule {
    private int id;
    private String ruleName;
    private int applyBeforeHours;
    private double refundPercentage;
    private Integer applyToPassengerTypeId;
    private Integer applyToCarriageTypeId;
    private String note;
    private boolean isActive;

   public RefundRule(int id, String ruleName, int applyBeforeHours, double refundPercentage,
                  Integer applyToPassengerTypeId, Integer applyToCarriageTypeId,
                  String note, boolean isActive) {
    this.id = id;
    this.ruleName = ruleName;
    this.applyBeforeHours = applyBeforeHours;
    this.refundPercentage = refundPercentage;
    this.applyToPassengerTypeId = applyToPassengerTypeId;
    this.applyToCarriageTypeId = applyToCarriageTypeId;
    this.note = note;
    this.isActive = isActive;
}

    public RefundRule() {
    }


   
    
    

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public int getApplyBeforeHours() { return applyBeforeHours; }
    public void setApplyBeforeHours(int applyBeforeHours) { this.applyBeforeHours = applyBeforeHours; }

    public double getRefundPercentage() { return refundPercentage; }
    public void setRefundPercentage(double refundPercentage) { this.refundPercentage = refundPercentage; }

    public Integer getApplyToPassengerTypeId() { return applyToPassengerTypeId; }
    public void setApplyToPassengerTypeId(Integer applyToPassengerTypeId) { this.applyToPassengerTypeId = applyToPassengerTypeId; }

    public Integer getApplyToCarriageTypeId() { return applyToCarriageTypeId; }
    public void setApplyToCarriageTypeId(Integer applyToCarriageTypeId) { this.applyToCarriageTypeId = applyToCarriageTypeId; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
