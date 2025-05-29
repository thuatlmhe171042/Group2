/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

public class Ticket {
    private int id;
    private Integer orderId;
    private int scheduleId;
    private int departureStopId;
    private int arrivalStopId;
    private int seatId;
    private int passengerTypeId;
    private String passengerName;
    private String passengerIdType;
    private String passengerIdNumber;
    private LocalDate passengerDateOfBirth;
    private String ticketStatus;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Ticket() {
    }

    public Ticket(int id, Integer orderId, int scheduleId, int departureStopId, int arrivalStopId, int seatId, int passengerTypeId, String passengerName, String passengerIdType, String passengerIdNumber, LocalDate passengerDateOfBirth, String ticketStatus, BigDecimal price, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.scheduleId = scheduleId;
        this.departureStopId = departureStopId;
        this.arrivalStopId = arrivalStopId;
        this.seatId = seatId;
        this.passengerTypeId = passengerTypeId;
        this.passengerName = passengerName;
        this.passengerIdType = passengerIdType;
        this.passengerIdNumber = passengerIdNumber;
        this.passengerDateOfBirth = passengerDateOfBirth;
        this.ticketStatus = ticketStatus;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Integer getOrderId() {
        return orderId;
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

    public int getSeatId() {
        return seatId;
    }

    public int getPassengerTypeId() {
        return passengerTypeId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerIdType() {
        return passengerIdType;
    }

    public String getPassengerIdNumber() {
        return passengerIdNumber;
    }

    public LocalDate getPassengerDateOfBirth() {
        return passengerDateOfBirth;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public void setPassengerTypeId(int passengerTypeId) {
        this.passengerTypeId = passengerTypeId;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setPassengerIdType(String passengerIdType) {
        this.passengerIdType = passengerIdType;
    }

    public void setPassengerIdNumber(String passengerIdNumber) {
        this.passengerIdNumber = passengerIdNumber;
    }

    public void setPassengerDateOfBirth(LocalDate passengerDateOfBirth) {
        this.passengerDateOfBirth = passengerDateOfBirth;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
