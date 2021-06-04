package ecma.ai.ussdapp.repository;

import ecma.ai.ussdapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    boolean findAllBySimCard_Client_FullName(String fullName);
}
