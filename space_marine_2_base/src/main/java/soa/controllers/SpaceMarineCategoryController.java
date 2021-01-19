package soa.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import soa.exception.BadSpaceMarineCategory;
import soa.models.SpaceMarine;
import soa.services.SpaceMarineService;

import java.util.List;

@Controller
@RequestMapping(value="/space/marine/category")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineCategoryController {
    private SpaceMarineService spaceMarineService;

    public SpaceMarineCategoryController(SpaceMarineService spaceMarineService) {
        this.spaceMarineService = spaceMarineService;
    }

    @GetMapping(value = "/{space-marine-category}", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<List<SpaceMarine>> get(@PathVariable("space-marine-category") Integer spaceMarineCategory){
        try {
            List<SpaceMarine> spaceMarineWhenCategoryGreater = spaceMarineService.findSpaceMarineWhenCategoryGreater(spaceMarineCategory);

            return ResponseEntity.ok(spaceMarineWhenCategoryGreater);
        } catch (BadSpaceMarineCategory badSpaceMarineCategory) {

            return ResponseEntity.badRequest().build();
        }
    }
}
