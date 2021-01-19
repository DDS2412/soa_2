package soa.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import soa.dto.FloatDto;
import soa.exception.BadSpaceMarineIdException;
import soa.services.SpaceMarineService;

import java.util.List;

@Controller
@RequestMapping(value="/space/marine/health")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineHealthController {
    private final SpaceMarineService spaceMarineService;

    public SpaceMarineHealthController(SpaceMarineService spaceMarineService) {
        this.spaceMarineService = spaceMarineService;
    }

    @GetMapping(value = "/mean", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<FloatDto> getAverageValue(@RequestParam("space_marine") List<Integer> spaceMarineIds) {
        try {
            return ResponseEntity.ok(new FloatDto(spaceMarineService.calculateHealthMeanValue(spaceMarineIds)));
        } catch (BadSpaceMarineIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{health-amount}")
    private ResponseEntity removeSpaceMarineByHealth(@PathVariable("health-amount") Long healthAmount){
        spaceMarineService.deleteSpaceMarineByHealth(healthAmount);
        return ResponseEntity.ok().build();
    }
}
