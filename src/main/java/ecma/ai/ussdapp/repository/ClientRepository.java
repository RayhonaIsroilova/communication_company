package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Client;
import ecma.ai.ussdapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByPassportNumber(String passportNumber);

    Optional<Client> findByPassportNumber(String number);

}
