package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineMeleeWeaponComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineMeleeWeaponComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = spaceMarine1.getMeleeWeapon().compareTo(spaceMarine2.getMeleeWeapon());
        return sortState ? result : -result;
    }
}
