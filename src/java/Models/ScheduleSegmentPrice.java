/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author thuat
 */
public class ScheduleSegmentPrice {
    private int id;
    private int scheduleId;
    private int departureStopId;
    private int arrivalStopId;
    private int carriageTypeId;
    private double price;

    public ScheduleSegmentPrice() {
    }

    public ScheduleSegmentPrice(int id, int scheduleId, int departureStopId, int arrivalStopId, int carriageTypeId, double price) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.departureStopId = departureStopId;
        this.arrivalStopId = arrivalStopId;
        this.carriageTypeId = carriageTypeId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDepartureStopId() {
        return departureStopId;
    }

    public void setDepartureStopId(int departureStopId) {
        this.departureStopId = departureStopId;
    }

    public int getArrivalStopId() {
        return arrivalStopId;
    }

    public void setArrivalStopId(int arrivalStopId) {
        this.arrivalStopId = arrivalStopId;
    }

    public int getCarriageTypeId() {
        return carriageTypeId;
    }

    public void setCarriageTypeId(int carriageTypeId) {
        this.carriageTypeId = carriageTypeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
}
