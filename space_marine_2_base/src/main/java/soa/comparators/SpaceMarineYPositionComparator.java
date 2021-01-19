package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineYPositionComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineYPositionComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = Long.compare(spaceMarine1.getCoordinates().getY(), spaceMarine2.getCoordinates().getY());
        return sortState ? result : -result;
    }
}
