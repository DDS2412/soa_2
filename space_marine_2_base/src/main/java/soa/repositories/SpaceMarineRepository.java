package soa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soa.enums.AstartesCategory;
import soa.models.SpaceMarine;

import javax.validation.constraints.Min;
import java.util.List;

@Repository
public interface SpaceMarineRepository extends JpaRepositoryImplementation<SpaceMarine, Integer> {
    List<SpaceMarine> findAllByHealth(long health);

    @Query(value = "select distinct s from SpaceMarine s where s.category > :category_value")
    List<SpaceMarine> findSpaceMarineWhenCategoryGreater(@Param("category_value") AstartesCategory categoryValue);
}
