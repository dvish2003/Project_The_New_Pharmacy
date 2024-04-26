package lk.ijse.gdse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Delivery {
    private String deliveryId;
    private String description;
    private BigDecimal price;
    private Date deliveryDate;
    private String deliveryStatus;
    private String deliveryMethod;
    private String deliveryAddress;
    private String contactInformation;
    private String trackingNumber;
}
