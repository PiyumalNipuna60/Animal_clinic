package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SpaAppointmentDTO {
    private String id;
    private int price;
    private String date;
    private String time;
    private String customerId;
    private String animalId;
    private String status;
}
