package models;

public class CartTicketInfo {
    public int scheduleId;
    public int fromStopId;
    public int toStopId;
    public int seatId;
    public int carriageId;
    public String carriageNumber;
    public String seatNumber;
    public String trainCode;
    public String carriageType;
    public String fromStationName;
    public String toStationName;
    public String departTime;
    public String arrivalTime;
    public double price;
    
    
    // + getter/setter

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public int getCarriageId() { return carriageId; }
    public void setCarriageId(int carriageId) { this.carriageId = carriageId; }

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

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getFromStopId() { return fromStopId; }
    public void setFromStopId(int fromStopId) { this.fromStopId = fromStopId; }

    public int getToStopId() { return toStopId; }
    public void setToStopId(int toStopId) { this.toStopId = toStopId; }
}


