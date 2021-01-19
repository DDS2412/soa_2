package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineChapterMarinesCountComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineChapterMarinesCountComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = Integer.compare(spaceMarine1.getChapter().getMarinesCount(), spaceMarine2.getChapter().getMarinesCount());
        return sortState ? result : -result;
    }
}
