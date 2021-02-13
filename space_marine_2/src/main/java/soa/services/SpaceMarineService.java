package soa.services;

import soa.model.SpaceMarine;

import java.util.Optional;

public interface SpaceMarineService {
    Optional<SpaceMarine> getSpaceMarine(Integer spaceMarineId);
}
