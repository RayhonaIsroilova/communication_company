package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailsRepository extends JpaRepository<Details, UUID> {
}
