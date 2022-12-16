package edu.itstep.hw20221130.models;

public class BusFlight {
    private long directionTime;
    private int directionPrice;
    private String departureTime;
    private String addressDeparture;
    private String addressArrival;
    private String enterpriseCarrier;

    public BusFlight(int directionTimeHour, int directionTimeMinute, int directionPrice, String departureTime, String addressDeparture, String addressArrival, String enterpriseCarrier) {
        this.directionTime = (directionTimeHour * 60 * 60 * 1000) + (directionTimeMinute * 60 * 1000);
        this.directionPrice = directionPrice;
        this.departureTime = departureTime;
        this.addressDeparture = addressDeparture;
        this.addressArrival = addressArrival;
        this.enterpriseCarrier = enterpriseCarrier;
    }

    public long getDirectionTime() {
        return directionTime;
    }

    public void setDirectionTime(long directionTime) {
        this.directionTime = directionTime;
    }

    public int getDirectionPrice() {
        return directionPrice;
    }

    public void setDirectionPrice(int directionPrice) {
        this.directionPrice = directionPrice;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getAddressDeparture() {
        return addressDeparture;
    }

    public void setAddressDeparture(String addressDeparture) {
        this.addressDeparture = addressDeparture;
    }

    public String getAddressArrival() {
        return addressArrival;
    }

    public void setAddressArrival(String addressArrival) {
        this.addressArrival = addressArrival;
    }

    public String getEnterpriseCarrier() {
        return enterpriseCarrier;
    }

    public void setEnterpriseCarrier(String enterpriseCarrier) {
        this.enterpriseCarrier = enterpriseCarrier;
    }

    @Override
    public String toString() {
        return "BusFlight{" +
                "directionTime=" + directionTime +
                ", directionPrice=" + directionPrice +
                ", departureTime='" + departureTime + '\'' +
                ", addressDeparture='" + addressDeparture + '\'' +
                ", addressArrival='" + addressArrival + '\'' +
                ", enterpriseCarrier='" + enterpriseCarrier + '\'' +
                '}';
    }
}
