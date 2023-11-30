package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SpaAppointmentTM {
    private String id;
    private int price;
    private String date;
    private String time;
    private String customerId;
    private String customerName;
    private String animalId;
    private String status;
}
