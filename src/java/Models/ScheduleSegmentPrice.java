/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

public class ScheduleSegmentPrice {
    private int id;
    private int scheduleId;
    private int departureStopId;
    private int arrivalStopId;
    private int carriageTypeId;
    private int seatTypeId;
    private BigDecimal price;

    public ScheduleSegmentPrice() {
    }

    public ScheduleSegmentPrice(int id, int scheduleId, int departureStopId, int arrivalStopId, int carriageTypeId, int seatTypeId, BigDecimal price) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.departureStopId = departureStopId;
        this.arrivalStopId = arrivalStopId;
        this.carriageTypeId = carriageTypeId;
        this.seatTypeId = seatTypeId;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getDepartureStopId() {
        return departureStopId;
    }

    public int getArrivalStopId() {
        return arrivalStopId;
    }

    public int getCarriageTypeId() {
        return carriageTypeId;
    }

    public int getSeatTypeId() {
        return seatTypeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setDepartureStopId(int departureStopId) {
        this.departureStopId = departureStopId;
    }

    public void setArrivalStopId(int arrivalStopId) {
        this.arrivalStopId = arrivalStopId;
    }

    public void setCarriageTypeId(int carriageTypeId) {
        this.carriageTypeId = carriageTypeId;
    }

    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
