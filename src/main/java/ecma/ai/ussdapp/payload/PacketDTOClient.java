package ecma.ai.ussdapp.payload;

import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.enums.PacketType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PacketDTOClient {
    private String name;
    private PacketType packetType;
    private int amount;
    private int price;
    private int duration;
    private Timestamp expireDate;

}
