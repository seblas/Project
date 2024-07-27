package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.project.domain.Game;
import pl.coderslab.project.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("select g from Game g where g.id =?1 and g.active=?2 and g.recruitmentEndTime<?3")
    Optional<Game> findByIdAndActiveAndTime(Long id, boolean active, LocalDateTime recruitmentEndTime);
}
