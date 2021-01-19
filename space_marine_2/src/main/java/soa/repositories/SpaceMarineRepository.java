package soa.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import soa.model.SpaceMarine;

@Repository
public interface SpaceMarineRepository extends JpaRepositoryImplementation<SpaceMarine, Integer> {
}
