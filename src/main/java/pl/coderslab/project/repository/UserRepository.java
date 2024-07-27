package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.project.domain.Game;
import pl.coderslab.project.domain.Role;
import pl.coderslab.project.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.level between ?1 and ?2 and u.dateOfBirth between ?3 and ?4")
    Set<User> findUserByLevelAndAge(int minLevel, int maxLevel, LocalDate startDate, LocalDate endDate);
}
