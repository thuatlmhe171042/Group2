/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

public class SeatType {
    private int id;
    private String typeName;
    private String description;
    private BigDecimal basePrice;

    public SeatType() {
    }

    public SeatType(int id, String typeName, String description, BigDecimal basePrice) {
        this.id = id;
        this.typeName = typeName;
        this.description = description;
        this.basePrice = basePrice;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

}
