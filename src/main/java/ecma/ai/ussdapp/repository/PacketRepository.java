package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacketRepository extends JpaRepository<Packet, UUID> {

    boolean existsByName(String name);

    ;
}
