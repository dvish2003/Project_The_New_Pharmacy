package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {
    private String supplierId;
    private String name;
    private String description;
    private String address;
    private String tel;


}

