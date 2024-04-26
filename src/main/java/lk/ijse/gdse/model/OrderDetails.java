package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {
    private String itemId;
    private String orderId;
    private int qty;
    private double unitPrice;
}
