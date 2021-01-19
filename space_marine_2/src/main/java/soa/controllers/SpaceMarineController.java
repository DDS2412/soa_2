package soa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import soa.dto.PageableSpaceMarinesDto;
import soa.exception.BadFilterException;
import soa.exception.NotFoundException;
import soa.model.SpaceMarine;
import soa.services.FilteringSpaceMarineService;

import java.util.HashMap;

@Controller
@RequestMapping(value="/space/marine")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineController {
    private final RestTemplate restTemplate;

    private final String endPoint;

    private final FilteringSpaceMarineService filteringSpaceMarineService;

    public SpaceMarineController(RestTemplate restTemplate, String endPoint,
                                 FilteringSpaceMarineService filteringSpaceMarineService){
        this.restTemplate = restTemplate;
        this.endPoint = endPoint;
        this.filteringSpaceMarineService = filteringSpaceMarineService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity createSpaceMarine(@RequestBody SpaceMarine newSpaceMarine) {
        try {
            String uri = endPoint + "space/marine/";
            newSpaceMarine.setCreationDate(null);
            restTemplate.postForObject(uri, newSpaceMarine, ResponseEntity.class);
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getCause());
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{space-marine-id}")
    private ResponseEntity deleteSpaceMarine(@PathVariable("space-marine-id") Integer spaceMarineId) {
        try {
            restTemplate.delete(endPoint + "space/marine/" + spaceMarineId);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            if (ex.getCause().getClass().equals(NotFoundException.class)){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @GetMapping(value = "/{space-marine-id}", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<SpaceMarine> getSpaceMarineById(@PathVariable("space-marine-id") Integer spaceMarineId){
        try {
            SpaceMarine spaceMarine = restTemplate.getForObject(endPoint + "space/marine/" + spaceMarineId, SpaceMarine.class);

            return ResponseEntity.ok(spaceMarine);
        } catch (Exception ex) {
            if (ex.getCause().getClass().equals(NotFoundException.class)){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PutMapping(value = "/{space-marine-id}", consumes = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<Void> updateSpaceMarine(@PathVariable("space-marine-id") Integer spaceMarineId,
                                                   @RequestBody SpaceMarine spaceMarine){
        try {
            String uri = endPoint + "space/marine/" + spaceMarineId;

            spaceMarine.setCreationDate(null);
            restTemplate.put(uri, spaceMarine, ResponseEntity.class);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            if (ex.getCause().getClass().equals(NotFoundException.class)){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
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

        String uri = endPoint + "space/marine/all?at_page=" + atPage + "&page_number=" + pageNumber;

        if (filterParams != null){
            uri += "&filter=" + String.join("&filter=", filterParams);
        }

        HashMap<String, Object> filteringHashMap = new HashMap<>();
        if (filteringCategory != null) {
            filteringHashMap.put("category", filteringCategory);
            uri += "&category=" + filteringCategory;
        }
        if (filteringName != null) {
            filteringHashMap.put("name", filteringName);
            uri += "&name=" + filteringName;
        }
        if (filteringHealth != null) {
            filteringHashMap.put("health", filteringHealth);
            uri += "&health=" + filteringHealth;
        }
        if (filteringHeight != null) {
            filteringHashMap.put("height", filteringHeight);
            uri += "&height=" + filteringHeight;
        }
        if (filteringMeleeWeapon != null) {
            filteringHashMap.put("meleeWeapon", filteringMeleeWeapon);
            uri += "&meleeWeapon=" + filteringMeleeWeapon;
        }
        if (filteringChapterName != null) {
            filteringHashMap.put("chapterName", filteringChapterName);
            uri += "&chapterName=" + filteringChapterName;
        }
        if (filteringX != null) {
            filteringHashMap.put("xPosition", filteringX);
            uri += "&xPosition=" + filteringX;
        }
        if (filteringY != null) {
            filteringHashMap.put("yPosition", filteringY);
            uri += "&yPosition=" + filteringY;
        }

        if(filterParams != null){
            try {
                filteringSpaceMarineService.validateFilteringObjectDto(filterParams, filteringHashMap);
            } catch (BadFilterException ex) {
                return ResponseEntity.badRequest().build();
            }
        }

        if ((atPage <= 0 || atPage > 50) || pageNumber <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (sortParams != null){
            uri += "&sort=" + String.join("&sort=", sortParams);

            if (sortState != null){
                uri += "&sort_state=" + sortState;
            }
        }

        try {
            PageableSpaceMarinesDto spaceMarines = restTemplate.getForObject(uri, PageableSpaceMarinesDto.class);

            return ResponseEntity.ok(spaceMarines);
        } catch (Exception ex) {
            if (ex.getCause().getClass().equals(NotFoundException.class)){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
