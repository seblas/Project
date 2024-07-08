package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.project.domain.Game;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {
}
