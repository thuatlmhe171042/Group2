package models;

public class Ticket {
    private int id;
    private String ticketCode;
    private Integer orderId;
    private int scheduleId;
    private int departureStopId;
    private int arrivalStopId;
    private int seatId;
    private int passengerTypeId;
    private String passengerName;
    private String passengerIdType;
    private String passengerIdNumber;
    private String passengerDateOfBirth;
    private String ticketStatus;
    private double price;
    private String createdAt;
    private String updatedAt;
    private boolean isDeleted;

    public Ticket() {}

    public Ticket(int id, String ticketCode, Integer orderId, int scheduleId, int departureStopId, int arrivalStopId,
                  int seatId, int passengerTypeId, String passengerName, String passengerIdType, String passengerIdNumber,
                  String passengerDateOfBirth, String ticketStatus, double price, String createdAt, String updatedAt, boolean isDeleted) {
        this.id = id;
        this.ticketCode = ticketCode;
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
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getPassengerTypeId() {
        return passengerTypeId;
    }

    public void setPassengerTypeId(int passengerTypeId) {
        this.passengerTypeId = passengerTypeId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerIdType() {
        return passengerIdType;
    }

    public void setPassengerIdType(String passengerIdType) {
        this.passengerIdType = passengerIdType;
    }

    public String getPassengerIdNumber() {
        return passengerIdNumber;
    }

    public void setPassengerIdNumber(String passengerIdNumber) {
        this.passengerIdNumber = passengerIdNumber;
    }

    public String getPassengerDateOfBirth() {
        return passengerDateOfBirth;
    }

    public void setPassengerDateOfBirth(String passengerDateOfBirth) {
        this.passengerDateOfBirth = passengerDateOfBirth;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
