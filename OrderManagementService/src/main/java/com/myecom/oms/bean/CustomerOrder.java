package com.myecom.oms.bean;

import com.myecom.oms.commons.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    private long orderId;
    private List<Item> items;
    private OrderStatus orderStatus;
    private Instant orderRecievedTimeStamp;
    private Instant orderUpdatedTimeStamp;
}
