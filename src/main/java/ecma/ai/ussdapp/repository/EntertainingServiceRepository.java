package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.EntertainingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntertainingServiceRepository extends JpaRepository<EntertainingService, UUID> {
    boolean existsByName(String name);
}
