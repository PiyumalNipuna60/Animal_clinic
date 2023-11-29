package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AnimalMaintainTm {
    private String id;
    private String name;
    private int age;
    private String categories;

    private String customerId;


}
