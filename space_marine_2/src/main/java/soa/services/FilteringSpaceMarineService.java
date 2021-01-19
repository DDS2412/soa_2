package soa.services;

import org.springframework.stereotype.Service;
import soa.dto.FilteringObjectDto;
import soa.enums.AstartesCategory;
import soa.enums.MeleeWeapon;
import soa.exception.BadFilterException;
import soa.model.SpaceMarine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilteringSpaceMarineService {
    public void validateFilteringObjectDto(String[] filterParams, HashMap<String, Object> filteringHashMap)
            throws BadFilterException {
        FilteringObjectDto filteringObjectDto = new FilteringObjectDto();

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
    }
}
