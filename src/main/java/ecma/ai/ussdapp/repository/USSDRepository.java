package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.Staff;
import ecma.ai.ussdapp.entity.UssdCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface USSDRepository extends JpaRepository<UssdCode, UUID> {


    Optional<UssdCode> findById(Integer id);
}
