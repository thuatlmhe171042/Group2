/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Carriages {
    private int id;
    private int trainId;
    private int carriageNumber;
    private int carriageTypeId;
    private int seatTypeId;

    public Carriages() {
    }

    public Carriages(int id, int trainId, int carriageNumber, int carriageTypeId, int seatTypeId) {
        this.id = id;
        this.trainId = trainId;
        this.carriageNumber = carriageNumber;
        this.carriageTypeId = carriageTypeId;
        this.seatTypeId = seatTypeId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getCarriageNumber() {
        return carriageNumber;
    }

    public int getCarriageTypeId() {
        return carriageTypeId;
    }

    public int getSeatTypeId() {
        return seatTypeId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setCarriageNumber(int carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public void setCarriageTypeId(int carriageTypeId) {
        this.carriageTypeId = carriageTypeId;
    }

    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

}
