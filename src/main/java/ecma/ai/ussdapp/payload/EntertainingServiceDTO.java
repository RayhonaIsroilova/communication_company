package ecma.ai.ussdapp.payload;

import lombok.Data;

@Data
public class EntertainingServiceDTO {
    private String name;
    private double price;
    private Integer serviceCategoryId;
    private int serviceTypeNumber;//ORDINAL
}
