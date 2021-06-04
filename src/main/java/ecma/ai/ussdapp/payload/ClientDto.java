package ecma.ai.ussdapp.payload;

import lombok.Data;

@Data
public class ClientDto {
    private String passportNumber;
    private String fullName;
    private Integer clientTypeOrdinal; //enum ordinal

    private BuyingSimCardDto buyingSimCardDto;
    //bir  nechta simkard olishni xoxlasa shu yerda bitta boshqa dto kk

}
