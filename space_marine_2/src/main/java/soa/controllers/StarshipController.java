package soa.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import soa.exception.BadSpaceMarineIdException;
import soa.exception.BadStarshipIdException;
import soa.exception.SpaceMarineDoesntExistInSpaceshipException;
import soa.exception.SpaceMarineJustHasSpaceshipTicketException;
import soa.model.Starship;
import soa.services.StarshipService;

import java.util.List;

@Controller
@RequestMapping(value="/starship")
@CrossOrigin(allowCredentials = "true")
public class StarshipController {
    private final StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @PostMapping(value = "/{starship-id}/load/{space-marine-id}")
    private ResponseEntity loadSpaceMarine(@PathVariable("starship-id") Long starshipId,
                                           @PathVariable("space-marine-id") Integer spaceMarineId){
        try {
            starshipService.loadSpaceMarine(spaceMarineId, starshipId);
        } catch (BadSpaceMarineIdException | BadStarshipIdException e) {
            return ResponseEntity.notFound().build();
        } catch (SpaceMarineJustHasSpaceshipTicketException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{starship-id}/unload/{space-marine-id}")
    private ResponseEntity unloadSpaceMarine(@PathVariable("starship-id") Long starshipId,
                                           @PathVariable("space-marine-id") Integer spaceMarineId){
        try {
            starshipService.unloadSpaceMarine(spaceMarineId, starshipId);
        } catch (BadSpaceMarineIdException | BadStarshipIdException e) {
            return ResponseEntity.notFound().build();
        } catch (SpaceMarineDoesntExistInSpaceshipException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/space/marine/{space-marine-id}", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<Boolean> checkAtStarship(@PathVariable("space-marine-id") Integer spaceMarineId){
        try {
            return ResponseEntity.ok(starshipService.checkAtSpaceMarine(spaceMarineId));
        } catch (BadSpaceMarineIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    private ResponseEntity<List<Starship>> allStarships(){
        List<Starship> allStarship = starshipService.getAllStarships();

        return ResponseEntity.ok(allStarship);
    }

    @PostMapping(value = "/new")
    private ResponseEntity createNewStarship(){
        starshipService.createNewStarship();
        return ResponseEntity.ok().build();
    }
}
