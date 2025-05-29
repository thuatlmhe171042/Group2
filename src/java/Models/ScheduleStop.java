/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

public class ScheduleStop {
    private int id;
    private int scheduleId;
    private int stationId;
    private int stopSequence;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public ScheduleStop() {
    }

    public ScheduleStop(int id, int scheduleId, int stationId, int stopSequence, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.stopSequence = stopSequence;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getStationId() {
        return stationId;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

}
