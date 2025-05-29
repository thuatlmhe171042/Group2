/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

public class Train {
    private int id;
    private String trainCode;
    private String trainName;
    private String description;
    private LocalDateTime createdAt;

    public Train() {
    }

    public Train(int id, String trainCode, String trainName, String description, LocalDateTime createdAt) {
        this.id = id;
        this.trainCode = trainCode;
        this.trainName = trainName;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
