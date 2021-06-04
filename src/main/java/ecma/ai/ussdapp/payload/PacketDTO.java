package ecma.ai.ussdapp.payload;

import ecma.ai.ussdapp.entity.SimCard;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class PacketDTO {
    @NotNull
    @Column(unique = true)
    private String name;
    private Integer packetTypeId;
    private int amount;
    private int price;
    private int duration;
    private Timestamp expireDate;
    private List<SimCard> simCardList;
}
