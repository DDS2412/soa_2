package soa.services;

import org.springframework.stereotype.Service;
import soa.dto.FilteringObjectDto;
import soa.enums.AstartesCategory;
import soa.enums.MeleeWeapon;
import soa.exception.BadFilterException;
import soa.models.SpaceMarine;
import soa.utils.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilteringSpaceMarineService {
    public List<SpaceMarine> filterBy(List<SpaceMarine> spaceMarines, FilteringObjectDto filteringObjectDto){
        return spaceMarines
                .stream()
                .filter(spaceMarine -> filterByArgs(spaceMarine, filteringObjectDto))
                .collect(Collectors.toList());
    }

    public FilteringObjectDto prepareFilteringObjectDto(String[] filterParams,  HashMap<String, Object> filteringHashMap)
            throws BadFilterException {
        FilteringObjectDto filteringObjectDto = new FilteringObjectDto();

        if (filterParams == null){
            return filteringObjectDto;
        }

        for (String param : filterParams){
            if (!filteringHashMap.containsKey(param)){
                throw new BadFilterException();
            }

            Object value = filteringHashMap.get(param);

            switch (param){
                case "name": {
                    filteringObjectDto.setName(((String)value).toLowerCase());
                    break;
                }
                case "health": {
                    filteringObjectDto.setHealth((Long) value);
                    break;
                }
                case "height": {
                    filteringObjectDto.setHeight((Integer) value);
                    break;
                }
                case "category": {
                    Optional<AstartesCategory> category = Arrays.stream(AstartesCategory.values())
                            .filter(c -> c.ordinal() == (Integer) value)
                            .findFirst();
                    filteringObjectDto.setCategory(category.orElse(null));
                    break;
                }
                case "meleeWeapon": {
                    Optional<MeleeWeapon> meleeWeapon = Arrays.stream(MeleeWeapon.values())
                            .filter(w -> w.ordinal() == (Integer) value)
                            .findFirst();
                    filteringObjectDto.setMeleeWeapon(meleeWeapon.orElse(null));
                    break;
                }
                case "chapterName": {
                    filteringObjectDto.setChapterName(((String) value).toLowerCase());
                    break;
                }
                case "chapterMarinesCount": {
                    filteringObjectDto.setChapterMarinesCount((Integer) value);
                    break;
                }
                case "xPosition": {
                    filteringObjectDto.setXPosition((Double) value);
                    break;
                }
                case "yPosition": {
                    filteringObjectDto.setYPosition((Long) value);
                    break;
                }
                default:{
                    throw new BadFilterException();
                }
            }
        }
        return filteringObjectDto;
    }

    private boolean filterByArgs(SpaceMarine spaceMarine, FilteringObjectDto filteringObjectDto) {
        boolean isValid = true;

        if (filteringObjectDto.getName() != null)
            isValid &= filteringObjectDto.getName().equals(spaceMarine.getName().toLowerCase());
        if (filteringObjectDto.getHealth() != null)
            isValid &= filteringObjectDto.getHealth().equals(spaceMarine.getHealth());
        if (filteringObjectDto.getHeight() != null)
            isValid &= filteringObjectDto.getHeight().equals(spaceMarine.getHeight());
        if (filteringObjectDto.getCategory() != null)
            isValid &= filteringObjectDto.getCategory().equals(spaceMarine.getCategory());
        if (filteringObjectDto.getMeleeWeapon() != null)
            isValid &= filteringObjectDto.getMeleeWeapon().equals(spaceMarine.getMeleeWeapon());
        if (filteringObjectDto.getChapterName() != null)
            isValid &= filteringObjectDto.getChapterName().equals(spaceMarine.getChapter().getName().toLowerCase());
        if (filteringObjectDto.getChapterMarinesCount() != null)
            isValid &= filteringObjectDto.getChapterMarinesCount().equals(spaceMarine.getChapter().getMarinesCount());
        if (filteringObjectDto.getXPosition() != null)
            isValid &= filteringObjectDto.getXPosition().equals(spaceMarine.getCoordinates().getX());
        if (filteringObjectDto.getYPosition() != null)
            isValid &= filteringObjectDto.getYPosition().equals(spaceMarine.getCoordinates().getY());

        return isValid;
    }
}
