package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.coderslab.project.domain.Field;
import pl.coderslab.project.domain.Game;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query("select f from Field f join f.address a order by a.city, a.street, a.number")
    List<Field> findAllSortedByStreet();
}
