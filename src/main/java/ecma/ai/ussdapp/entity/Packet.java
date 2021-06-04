package ecma.ai.ussdapp.entity;

import ecma.ai.ussdapp.entity.enums.PacketType;
import ecma.ai.ussdapp.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Packet extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private PacketType packetType;

    private String name;

    private int amount;

    private int price;

    private int duration; //5 kunlik

    private Timestamp expireDate;

    @ManyToMany
    private List<SimCard> simCardList;

}
