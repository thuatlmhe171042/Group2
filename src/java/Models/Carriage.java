/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author thuat
 */

    
   public class Carriage {
    private int id;
    private int trainId;
    private int carriageTypeId;
    private int carriageNumber;
    private boolean isDeleted;

    public Carriage(int id, int trainId, int carriageTypeId, int carriageNumber, boolean isDeleted) {
        this.id = id;
        this.trainId = trainId;
        this.carriageTypeId = carriageTypeId;
        this.carriageNumber = carriageNumber;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getCarriageTypeId() {
        return carriageTypeId;
    }

    public void setCarriageTypeId(int carriageTypeId) {
        this.carriageTypeId = carriageTypeId;
    }

    public int getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(int carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}


    
    
    

