package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String employeeId;
    private String name;
    private String NICNo;
    private String address;
    private String tel;
    private double salary;
}

