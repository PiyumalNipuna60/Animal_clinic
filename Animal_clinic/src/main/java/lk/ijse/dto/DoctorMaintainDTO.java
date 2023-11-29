package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DoctorMaintainDTO {
    private String id;
    private String name;
    private String discription;
    private String email;
    private int phone;
    private byte[] image;
    private String userID;
}
