package soa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soa.model.Starship;

import java.util.List;

@Repository
public interface StarshipRepository extends JpaRepositoryImplementation<Starship, Long> {
}
