package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderId;

    private String description;
    private BigDecimal paymentAmount;
    private Date date;
    private String cuId;
    private String payId;
    private String employeeId;
}