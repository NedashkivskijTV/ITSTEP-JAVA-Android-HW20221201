package edu.itstep.hw20221130.models;

import java.util.ArrayList;
import java.util.List;

public class DirectionDB {
    private List<Direction> directionList = new ArrayList<>();

    public DirectionDB() {
    }

    public DirectionDB(List<Direction> directionList) {
        this.directionList = directionList;
    }

    public List<Direction> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(List<Direction> directionList) {
        this.directionList = directionList;
    }

    public String[] getDirectionsNamesArr() {
        String[] directionsNamesArr = new String[directionList.size()];
        int i = 0;
        for (Direction direction : directionList) {
            directionsNamesArr[i] = direction.getDirectionName();
            ++i;
        }
        return directionsNamesArr;
    }

    public String[] getDepartureTimeArr(int directionPosition){
        String[] departureTimeArr = new String[directionList.get(directionPosition).getBusFlightList().size()];
        int i = 0;
        for (BusFlight busFlight : directionList.get(directionPosition).getBusFlightList()) {
            departureTimeArr[i] = busFlight.getDepartureTime();
            ++i;
        }
        return departureTimeArr;
    }
}
