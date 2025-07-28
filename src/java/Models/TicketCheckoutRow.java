package models;

public class TicketCheckoutRow {
    private int seatId;
    private int scheduleId;
    private int carriageId;
    private int departureStopId;
    private int arrivalStopId;
    private String carriageNumber;
    private String seatNumber;
    private String trainCode;
    private String carriageType;
    private String fromStationName;
    private String toStationName;
    private String departTime;
    private String arrivalTime;
    private double basePrice;

    // getter/setter
    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public int getCarriageId() { return carriageId; }
    public void setCarriageId(int carriageId) { this.carriageId = carriageId; }

    public int getDepartureStopId() { return departureStopId; }
    public void setDepartureStopId(int departureStopId) { this.departureStopId = departureStopId; }

    public int getArrivalStopId() { return arrivalStopId; }
    public void setArrivalStopId(int arrivalStopId) { this.arrivalStopId = arrivalStopId; }

    public String getCarriageNumber() { return carriageNumber; }
    public void setCarriageNumber(String carriageNumber) { this.carriageNumber = carriageNumber; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getTrainCode() { return trainCode; }
    public void setTrainCode(String trainCode) { this.trainCode = trainCode; }

    public String getCarriageType() { return carriageType; }
    public void setCarriageType(String carriageType) { this.carriageType = carriageType; }

    public String getFromStationName() { return fromStationName; }
    public void setFromStationName(String fromStationName) { this.fromStationName = fromStationName; }

    public String getToStationName() { return toStationName; }
    public void setToStationName(String toStationName) { this.toStationName = toStationName; }

    public String getDepartTime() { return departTime; }
    public void setDepartTime(String departTime) { this.departTime = departTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
}
