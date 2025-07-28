// models/SegmentInfo.java
package models;

import java.util.Map;

public class SegmentInfo {
    private ScheduleStop fromStop;
    private ScheduleStop toStop;
    private double distance; // km
    private Map<Integer, Double> prices;         // Key = carriageTypeId, Value = giá đã nhập (nếu có)
    private Map<Integer, Double> suggestedPrices; // Key = carriageTypeId, Value = giá gợi ý (nếu chưa nhập)

    public ScheduleStop getFromStop() { return fromStop; }
    public void setFromStop(ScheduleStop fromStop) { this.fromStop = fromStop; }
    public ScheduleStop getToStop() { return toStop; }
    public void setToStop(ScheduleStop toStop) { this.toStop = toStop; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public Map<Integer, Double> getPrices() { return prices; }
    public void setPrices(Map<Integer, Double> prices) { this.prices = prices; }
    public Map<Integer, Double> getSuggestedPrices() { return suggestedPrices; }
    public void setSuggestedPrices(Map<Integer, Double> suggestedPrices) { this.suggestedPrices = suggestedPrices; }
}
