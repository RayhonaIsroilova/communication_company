package ecma.ai.ussdapp.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class StaffDto {
    private String fullName;
    private String userName;
    private Integer roleId;

    private UUID filialId;
    private String position; //hr
    private String password;
}
