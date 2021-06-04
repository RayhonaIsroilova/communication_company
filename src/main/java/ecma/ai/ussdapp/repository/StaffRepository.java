package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByUserName(String username);

    boolean existsByUserName(String username);
}
