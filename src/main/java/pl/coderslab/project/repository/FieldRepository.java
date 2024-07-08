package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.coderslab.project.domain.Field;

@Repository
@Transactional
public interface FieldRepository extends JpaRepository<Field, Long> {
}
