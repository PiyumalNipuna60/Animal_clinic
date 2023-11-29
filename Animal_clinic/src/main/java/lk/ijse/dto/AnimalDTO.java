package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AnimalDTO {
    private String id;
    private String name;
    private int age;
    private String Type;

    private byte[] image;

    private String customerId;


}
