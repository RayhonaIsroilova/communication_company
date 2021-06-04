package ecma.ai.ussdapp.payload;

import ecma.ai.ussdapp.entity.SimCard;
import ecma.ai.ussdapp.entity.enums.ActionType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
public class DetailsDTO {
    @Enumerated(EnumType.STRING)
    private ActionType actionType;
    private SimCard simCard;
    //ayni vaqtda qancha sarfladi! //8 min
    private double amount;
}
