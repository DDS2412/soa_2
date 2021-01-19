package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineChapterNameComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineChapterNameComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = spaceMarine1.getChapter().getName().compareTo(spaceMarine2.getChapter().getName());
        return sortState ? result : -result;
    }
}
