package soa.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import soa.dto.FloatDto;
import soa.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/space/marine/health")
@CrossOrigin(allowCredentials = "true")
public class SpaceMarineHealthController {
    private final RestTemplate restTemplate;

    private final String endPoint;

    public SpaceMarineHealthController(RestTemplate restTemplate, String endPoint){
        this.restTemplate = restTemplate;
        this.endPoint = endPoint;
    }

    @GetMapping(value = "/mean", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<FloatDto> getAverageValue(@RequestParam("space_marine") List<Integer> spaceMarineIds) {
        String spaceMarineIdsString = spaceMarineIds
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("&space_marine="));

        String uri = endPoint + "space/marine/health/mean?space_marine=" + spaceMarineIdsString;

        try {
            ResponseEntity<FloatDto> meanHealth = restTemplate.getForEntity(uri, FloatDto.class);

            return ResponseEntity.ok(meanHealth.getBody());
        } catch (Exception ex) {
            if (ex.getCause().getClass().equals(NotFoundException.class)){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping(value = "{health-amount}")
    private ResponseEntity removeSpaceMarineByHealth(@PathVariable("health-amount") Long healthAmount){
        restTemplate.delete(endPoint + "space/marine/health/" + healthAmount);

        return ResponseEntity.ok().build();
    }
}
