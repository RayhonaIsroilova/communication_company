package ecma.ai.ussdapp.payload;

import ecma.ai.ussdapp.entity.Staff;
import lombok.Data;

import java.util.List;

@Data
public class FilialDto {

    private String name;

    private StaffDto staffDto;

    private List<String> staffUsernames;


}
