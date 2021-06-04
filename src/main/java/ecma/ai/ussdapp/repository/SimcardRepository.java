package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SimcardRepository extends JpaRepository<SimCard, UUID> {

    Optional<SimCard> findByCodeAndNumber(String code, String number);

    Optional<SimCard> findBySimCardNumber(String simcardNumber);


}
