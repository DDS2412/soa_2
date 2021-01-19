package soa.services.impl;

import org.springframework.stereotype.Service;
import soa.dto.FilteringObjectDto;
import soa.dto.PageableSpaceMarinesDto;
import soa.enums.AstartesCategory;
import soa.exception.BadSortException;
import soa.exception.BadSpaceMarineCategory;
import soa.exception.BadSpaceMarineIdException;
import soa.models.SpaceMarine;
import soa.repositories.SpaceMarineRepository;
import soa.services.FilteringSpaceMarineService;
import soa.services.SortSpaceMarineService;
import soa.services.SpaceMarineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceMarineServiceImpl implements SpaceMarineService {
    private final SpaceMarineRepository spaceMarineRepository;

    private final FilteringSpaceMarineService filteringSpaceMarineService;

    private final SortSpaceMarineService sortSpaceMarineService;

    public SpaceMarineServiceImpl(SpaceMarineRepository spaceMarineRepository){
        this.spaceMarineRepository = spaceMarineRepository;
        this.filteringSpaceMarineService = new FilteringSpaceMarineService();
        this.sortSpaceMarineService = new SortSpaceMarineService();
    }

    @Override
    public void saveSpaceMarine(SpaceMarine spaceMarine){
         spaceMarineRepository.save(spaceMarine);
    }

    @Override
    public void updateSpaceMarine(Integer spaceMarineId, SpaceMarine updatedSpaceMarine) throws BadSpaceMarineIdException {
        Optional<SpaceMarine> optionalOldSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
        SpaceMarine oldSpaceMarine = optionalOldSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

        updatedSpaceMarine.setId(spaceMarineId);
        updatedSpaceMarine.setCreationDate(oldSpaceMarine.getCreationDate());

        spaceMarineRepository.save(updatedSpaceMarine);
    }

    @Override
    public void deleteSpaceMarineById(Integer spaceMarineId) throws BadSpaceMarineIdException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
        SpaceMarine spaceMarine = optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

        spaceMarineRepository.delete(spaceMarine);
    }

    @Override
    public void deleteSpaceMarineByHealth(long health) {
        List<SpaceMarine> spaceMarines = spaceMarineRepository.findAllByHealth(health);
        spaceMarines.forEach(spaceMarineRepository::delete);
    }

    @Override
    public float calculateHealthMeanValue(List<Integer> spaceMarineIds) throws BadSpaceMarineIdException {
        List<SpaceMarine> spaceMarines = new ArrayList<>();

        for (Integer spaceMarineId : spaceMarineIds){
            Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
            SpaceMarine spaceMarine = optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

            spaceMarines.add(spaceMarine);
        }

        float result = 0;
        for (SpaceMarine spaceMarine : spaceMarines){
            result += (float) spaceMarine.getHealth();
        }

        if (spaceMarines.size() == 0) return -1;

        return result / spaceMarines.size();
    }

    @Override
    public PageableSpaceMarinesDto findAllSpaceMarines(
            Integer atPage,
            Integer pageNumber,
            FilteringObjectDto filteringObjectDto,
            String[] sortParams,
            String sortState) throws BadSortException {

        List<SpaceMarine> spaceMarines = spaceMarineRepository.findAll();
        spaceMarines = filteringSpaceMarineService.filterBy(spaceMarines, filteringObjectDto);

        spaceMarines = sortSpaceMarineService.sortByParams(spaceMarines, sortParams, sortState);

        return getPageableSpaceMarinesDto(spaceMarines, atPage, pageNumber);
    }

    @Override
    public SpaceMarine findSpaceMarine(Integer id) throws BadSpaceMarineIdException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(id);

        return optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);
    }

    @Override
    public List<SpaceMarine> findSpaceMarineWhenCategoryGreater(Integer intCategory) throws BadSpaceMarineCategory {
        if (intCategory > AstartesCategory.values().length - 1) throw new BadSpaceMarineCategory();

        return spaceMarineRepository.findSpaceMarineWhenCategoryGreater(AstartesCategory.values()[intCategory]);
    }

    private PageableSpaceMarinesDto getPageableSpaceMarinesDto(List<SpaceMarine> spaceMarines, Integer atPage, Integer pageNumber){
        Integer totalPages;
        Integer elementsAtCurrentPage;

        if (spaceMarines.size() % atPage == 0){
            totalPages = spaceMarines.size() / atPage;
        } else {
            totalPages = spaceMarines.size() / atPage + 1;
        }

        if (totalPages < pageNumber) return null;
        if (totalPages == 0) return null;

        if (totalPages.equals(pageNumber)){
            elementsAtCurrentPage = (spaceMarines.size() - (pageNumber - 1) * atPage) % (atPage + 1);
        } else {
            elementsAtCurrentPage = atPage;
        }

        Integer firstElementId = (pageNumber - 1) * atPage;
        Integer lastElementId = firstElementId + elementsAtCurrentPage;

        List<SpaceMarine> resultSpaceMarines = spaceMarines.subList(firstElementId, lastElementId);

        return new PageableSpaceMarinesDto(resultSpaceMarines, pageNumber, totalPages,  atPage, spaceMarines.size(), totalPages.equals(pageNumber));
    }
}
