package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Filial;
import ecma.ai.ussdapp.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilialRepository extends JpaRepository<Filial, UUID> {

    void findById(Integer id);
}
