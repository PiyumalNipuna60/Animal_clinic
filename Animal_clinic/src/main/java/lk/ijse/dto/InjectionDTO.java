package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class InjectionDTO {
    private String id;
    private String name;
    private String number_of_times;
    private String categories;
    private String state;
    private String Start_date;
    private String end_date;
    private String animalId;

}
