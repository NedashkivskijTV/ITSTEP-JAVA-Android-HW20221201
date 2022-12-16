package edu.itstep.hw20221130.models;

import java.util.ArrayList;
import java.util.List;

public class Direction {
    //private String directionName;
    private String directionTownStart;
    private String directionTownFinish;
    private int directionDistance;
    private List<BusFlight> busFlightList = new ArrayList<>();

    public Direction(String directionTownStart, String directionTownFinish, int directionDistance, List<BusFlight> busFlightList) {
        this.directionTownStart = directionTownStart;
        this.directionTownFinish = directionTownFinish;
        this.directionDistance = directionDistance;
        this.busFlightList = busFlightList;
    }

    public String getDirectionTownStart() {
        return directionTownStart;
    }

    public void setDirectionTownStart(String directionTownStart) {
        this.directionTownStart = directionTownStart;
    }

    public String getDirectionTownFinish() {
        return directionTownFinish;
    }

    public void setDirectionTownFinish(String directionTownFinish) {
        this.directionTownFinish = directionTownFinish;
    }

    public int getDirectionDistance() {
        return directionDistance;
    }

    public void setDirectionDistance(int directionDistance) {
        this.directionDistance = directionDistance;
    }

    public List<BusFlight> getBusFlightList() {
        return busFlightList;
    }

    public void setBusFlightList(List<BusFlight> busFlightList) {
        this.busFlightList = busFlightList;
    }

    public String getDirectionName() {
        return directionTownStart + "-" + directionTownFinish;
    }
}
