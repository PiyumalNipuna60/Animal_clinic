package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CustomerTm {
    private String id;
    private String name;
    private String email;
    private int c_number;
    private String address;

    private String userId;


}
