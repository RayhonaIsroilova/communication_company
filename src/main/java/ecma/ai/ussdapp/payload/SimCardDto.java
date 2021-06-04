package ecma.ai.ussdapp.payload;

import lombok.Data;

@Data
public class SimCardDto {
    private String code;
    private String simCardNumber;
    private String pinCode;
    private String number;
    private String name;
}
