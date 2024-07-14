package pl.coderslab.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.project.domain.Address;
import pl.coderslab.project.domain.Field;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
}
