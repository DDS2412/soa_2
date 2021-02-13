package soa.services.impl;

import org.springframework.stereotype.Service;
import soa.exception.BadSpaceMarineIdException;
import soa.exception.BadStarshipIdException;
import soa.exception.SpaceMarineDoesntExistInSpaceshipException;
import soa.exception.SpaceMarineJustHasSpaceshipTicketException;
import soa.model.SpaceMarine;
import soa.model.Starship;
import soa.repositories.SpaceMarineRepository;
import soa.repositories.StarshipRepository;
import soa.services.SpaceMarineService;
import soa.services.StarshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarshipServiceImpl implements StarshipService {
    private final SpaceMarineRepository spaceMarineRepository;

    private final StarshipRepository starshipRepository;

    private final SpaceMarineService spaceMarineService;

    public StarshipServiceImpl(SpaceMarineRepository spaceMarineRepository,
                               StarshipRepository starshipRepository,
                               SpaceMarineService spaceMarineService) {
        this.spaceMarineRepository = spaceMarineRepository;
        this.starshipRepository = starshipRepository;
        this.spaceMarineService = spaceMarineService;

        createNewStarship();
    }

    @Override
    public void loadSpaceMarine(Integer spaceMarineId, Long starshipId) throws BadSpaceMarineIdException, BadStarshipIdException, SpaceMarineJustHasSpaceshipTicketException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineService.getSpaceMarine(spaceMarineId);
        SpaceMarine spaceMarine = optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

        Optional<Starship> optionalStarship = starshipRepository.findById(starshipId);
        Starship starship = optionalStarship.orElseThrow(BadStarshipIdException::new);
        if (spaceMarine.getStarship() != null){
            throw new SpaceMarineJustHasSpaceshipTicketException();
        }
        spaceMarine.setStarship(starship);
        starship.getSpaceMarines().add(spaceMarine);

        starshipRepository.save(starship);
    }

    @Override
    public void unloadSpaceMarine(Integer spaceMarineId, Long starshipId)
            throws BadSpaceMarineIdException, BadStarshipIdException, SpaceMarineDoesntExistInSpaceshipException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineService.getSpaceMarine(spaceMarineId);
        SpaceMarine spaceMarine = optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

        Optional<Starship> optionalStarship = starshipRepository.findById(starshipId);
        Starship starship = optionalStarship.orElseThrow(BadStarshipIdException::new);

        if(starship.getSpaceMarines().removeIf(spaceMarinePredicate -> spaceMarinePredicate.getId().equals(spaceMarineId))){
            starshipRepository.save(starship);
            spaceMarineRepository.save(spaceMarine.setStarship(null).setId(spaceMarineId));
        } else {
            throw new SpaceMarineDoesntExistInSpaceshipException();
        }
    }

    @Override
    public List<Starship> getAllStarships() {
        return starshipRepository.findAll();
    }

    @Override
    public void createNewStarship() {
        List<Long> allIds = starshipRepository
                .findAll()
                .stream()
                .map(Starship::getId)
                .collect(Collectors.toList());

        if (allIds.size() == 0){
            starshipRepository.save(new Starship().setSpaceMarines(new ArrayList<>()).setName("Spaceship 1"));
        }
    }

    @Override
    public Boolean checkAtSpaceMarine(Integer spaceMarineId) throws BadSpaceMarineIdException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineService.getSpaceMarine(spaceMarineId);
        SpaceMarine spaceMarine = optionalSpaceMarine.orElseThrow(BadSpaceMarineIdException::new);

        if (spaceMarine.getStarship() == null){
            return false;
        } else {
            return true;
        }
    }
}
