package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String itemId;
    private String description;
    private double unitPrice;
    private int QtyOnHand;
    private String stockId;


}
