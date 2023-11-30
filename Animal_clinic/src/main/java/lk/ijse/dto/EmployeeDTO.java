package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EmployeeDTO {

    private String id;
    private String name;
    private String address;
    private String age;
    private String email;
    private String contact;
    private String salary;
}
