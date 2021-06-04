package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TariffRepository extends JpaRepository<Tariff, UUID> {

    boolean existsByName(String name);
}
