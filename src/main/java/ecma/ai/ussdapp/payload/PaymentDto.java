package ecma.ai.ussdapp.payload;

import ecma.ai.ussdapp.entity.enums.PayType;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentDto {
    private PayType payType;
    private double amount;

    private String payerName;

    private String payerId;
    private UUID simCardID;

}
