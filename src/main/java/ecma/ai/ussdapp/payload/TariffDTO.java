package ecma.ai.ussdapp.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class TariffDTO {

    @NotNull
    @Column(unique = true)
    private String name;
    private double price;
    private double switchPrice;
    private Timestamp expireDate;
    private int mb;
    private int sms;
    private int min;
    private int mbCost;
    private int smsCost;
    private int minCost;
}
