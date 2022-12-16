package edu.itstep.hw20221130.models;

import java.io.Serializable;

public class OrderTicket implements Serializable {
    private String direction;
    private String dateOfDirection;
    private String countPlaces;
    private String departureTime;
    private String oneTicketCost;

    private int directionPosition; // використовується тільки при повороті екрана
    private int timePosition; // використовується тільки при повороті екрана

    private boolean isDirectionChanged; // індикатор, що використовується для забезпечення
    // коректного виведенні часу відправлення при зміні напрямку та у наслідок зміни положення екрана

    public OrderTicket() {
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDateOfDirection() {
        return dateOfDirection;
    }

    public void setDateOfDirection(String dateOfDirection) {
        this.dateOfDirection = dateOfDirection;
    }

    public String getCountPlaces() {
        return countPlaces;
    }

    public void setCountPlaces(String countPlaces) {
        this.countPlaces = countPlaces;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getOneTicketCost() {
        return oneTicketCost;
    }

    public void setOneTicketCost(String oneTicketCost) {
        this.oneTicketCost = oneTicketCost;
    }

    public int getDirectionPosition() {
        return directionPosition;
    }

    public void setDirectionPosition(int directionPosition) {
        this.directionPosition = directionPosition;
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

    public boolean isDirectionChanged() {
        return isDirectionChanged;
    }

    public void setDirectionChanged(boolean directionChanged) {
        isDirectionChanged = directionChanged;
    }

}
