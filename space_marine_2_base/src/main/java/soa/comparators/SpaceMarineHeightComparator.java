package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineHeightComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineHeightComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = Integer.compare(spaceMarine1.getHeight(), spaceMarine2.getHeight());
        return sortState ? result : -result;
    }
}
