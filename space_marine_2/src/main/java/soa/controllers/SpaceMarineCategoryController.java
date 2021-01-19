package soa.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import soa.model.SpaceMarine;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value="/space/marine/category")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineCategoryController {
    private final RestTemplate restTemplate;

    private final String endPoint;

    public SpaceMarineCategoryController(RestTemplate restTemplate, String endPoint){
        this.restTemplate = restTemplate;
        this.endPoint = endPoint;
    }

    @GetMapping(value = "/{space-marine-category}", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<List<SpaceMarine>> get(@PathVariable("space-marine-category") Integer spaceMarineCategory){
        try {
            String uri = endPoint + "space/marine/category/" + spaceMarineCategory;
            SpaceMarine[] spaceMarines = restTemplate.getForObject(uri, SpaceMarine[].class);

            return ResponseEntity.ok(Arrays.asList(spaceMarines));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
