package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineXPositionComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineXPositionComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = Double.compare(spaceMarine1.getCoordinates().getX(), spaceMarine2.getCoordinates().getX());
        return sortState ? result : -result;
    }
}
