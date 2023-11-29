package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ServicesDTO {
    private String id;
    private String type;
    private String price;
    private String startTime;
    private String endTime;


}
