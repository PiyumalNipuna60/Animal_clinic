package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ScheduleTm {
    private String id;
    private String name;
    private String time;
    private String date;

    private String doctorId;
    private String appointmentId;


}
