package soa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import soa.dto.FilteringObjectDto;
import soa.dto.PageableSpaceMarinesDto;
import soa.exception.BadFilterException;
import soa.exception.BadSortException;
import soa.exception.BadSpaceMarineIdException;
import soa.models.SpaceMarine;
import soa.services.FilteringSpaceMarineService;
import soa.services.SpaceMarineService;

import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@RequestMapping(value="/space/marine")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineController {
    private final SpaceMarineService spaceMarineService;
    private final FilteringSpaceMarineService filteringSpaceMarineService;

    public SpaceMarineController(SpaceMarineService spaceMarineService,
                                 FilteringSpaceMarineService filteringSpaceMarineService) {
        this.spaceMarineService = spaceMarineService;
        this.filteringSpaceMarineService = filteringSpaceMarineService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity createSpaceMarine(@RequestBody SpaceMarine newSpaceMarine) {
        newSpaceMarine.setCreationDate(LocalDateTime.now());
        spaceMarineService.saveSpaceMarine(newSpaceMarine);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{space-marine-id}")
    private ResponseEntity deleteSpaceMarine(@PathVariable("space-marine-id") Integer spaceMarineId) {
        try {
            spaceMarineService.deleteSpaceMarineById(spaceMarineId);
        } catch (BadSpaceMarineIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/{space-marine-id}", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<SpaceMarine> getSpaceMarineById(@PathVariable("space-marine-id") Integer spaceMarineId){
        try {
            SpaceMarine spaceMarine = spaceMarineService.findSpaceMarine(spaceMarineId);
            return ResponseEntity.ok(spaceMarine);
        } catch (BadSpaceMarineIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{space-marine-id}", consumes = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<Void> updateSpaceMarine(@PathVariable("space-marine-id") Integer spaceMarineId,
                                                   @RequestBody SpaceMarine spaceMarine){
        try {
            spaceMarineService.updateSpaceMarine(spaceMarineId, spaceMarine);
            return ResponseEntity.ok().build();
        } catch (BadSpaceMarineIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<PageableSpaceMarinesDto> getAllSpaceMarines(
            @RequestParam(name = "at_page") Integer atPage,
            @RequestParam(name = "page_number") Integer pageNumber,
            @RequestParam(name = "filter", required = false) String[] filterParams,
            @RequestParam(name = "category", required = false) Integer filteringCategory,
            @RequestParam(name = "name", required = false) String filteringName,
            @RequestParam(name = "health", required = false) Long filteringHealth,
            @RequestParam(name = "height", required = false) Integer filteringHeight,
            @RequestParam(name = "meleeWeapon", required = false) Integer filteringMeleeWeapon,
            @RequestParam(name = "chapterName", required = false) String filteringChapterName,
            @RequestParam(name = "xPosition", required = false) Double filteringX,
            @RequestParam(name = "yPosition", required = false) Long filteringY,
            @RequestParam(name = "sort", required = false) String[] sortParams,
            @RequestParam(name = "sort_state", required = false) String sortState) {
        FilteringObjectDto filteringObjectDto;
        HashMap<String, Object> filteringHashMap = new HashMap<>();
        if (filteringCategory != null) {
            filteringHashMap.put("category", filteringCategory);
        }
        if (filteringName != null) {
            filteringHashMap.put("name", filteringName);
        }
        if (filteringHealth != null) {
            filteringHashMap.put("health", filteringHealth);
        }
        if (filteringHeight != null) {
            filteringHashMap.put("height", filteringHeight);
        }
        if (filteringMeleeWeapon != null) {
            filteringHashMap.put("meleeWeapon", filteringMeleeWeapon);
        }
        if (filteringChapterName != null) {
            filteringHashMap.put("chapterName", filteringChapterName);
        }
        if (filteringX != null) {
            filteringHashMap.put("xPosition", filteringX);
        }
        if (filteringY != null) {
            filteringHashMap.put("yPosition", filteringY);
        }

        try {
            filteringObjectDto = filteringSpaceMarineService.prepareFilteringObjectDto(filterParams, filteringHashMap);
        } catch (BadFilterException ex) {
            return ResponseEntity.badRequest().build();
        }

        if ((atPage <= 0 || atPage > 50) || pageNumber <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            PageableSpaceMarinesDto spaceMarines = spaceMarineService.findAllSpaceMarines(
                    atPage, pageNumber, filteringObjectDto,
                    sortParams, sortState);

            return ResponseEntity.ok(spaceMarines);
        } catch (BadSortException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
