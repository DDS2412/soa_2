package soa.services;

import soa.exception.BadSpaceMarineIdException;
import soa.exception.BadStarshipIdException;
import soa.exception.SpaceMarineDoesntExistInSpaceshipException;
import soa.exception.SpaceMarineJustHasSpaceshipTicketException;
import soa.model.Starship;

import java.util.List;

public interface StarshipService {
    void loadSpaceMarine(Integer spaceMarineId, Long starshipId) throws BadSpaceMarineIdException, BadStarshipIdException, SpaceMarineJustHasSpaceshipTicketException;

    void unloadSpaceMarine(Integer spaceMarineId, Long starshipId) throws BadSpaceMarineIdException, BadStarshipIdException, SpaceMarineDoesntExistInSpaceshipException;

    List<Starship> getAllStarships();

    void createNewStarship();

    Boolean checkAtSpaceMarine(Integer spaceMarineId) throws BadSpaceMarineIdException;
}
