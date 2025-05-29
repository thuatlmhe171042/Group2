/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

public class TrainSchedule {
    private int id;
    private int trainId;
    private int departureStationId;
    private int arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;

    public TrainSchedule() {
    }

    public TrainSchedule(int id, int trainId, int departureStationId, int arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, String status) {
        this.id = id;
        this.trainId = trainId;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getDepartureStationId() {
        return departureStationId;
    }

    public int getArrivalStationId() {
        return arrivalStationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setDepartureStationId(int departureStationId) {
        this.departureStationId = departureStationId;
    }

    public void setArrivalStationId(int arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
