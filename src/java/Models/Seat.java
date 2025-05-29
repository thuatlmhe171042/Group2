/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Seat {
    private int id;
    private int carriageId;
    private String seatNumber;

    public Seat() {
    }

    public Seat(int id, int carriageId, String seatNumber) {
        this.id = id;
        this.carriageId = carriageId;
        this.seatNumber = seatNumber;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

}
