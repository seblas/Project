package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.project.domain.Invitation;

@Repository
@Transactional
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
