package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stock {
    private String stockId;
    private String description;
    private String category;
    private int qtyOnHand;
}
