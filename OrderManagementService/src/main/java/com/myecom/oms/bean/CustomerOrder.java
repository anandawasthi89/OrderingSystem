package com.myecom.oms.bean;

import com.myecom.oms.commons.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @ElementCollection@CollectionTable(name = "Order_Items", joinColumns = @JoinColumn(name = "orderId"))
    private List<ItemByQuantity> items;
    private OrderStatus orderStatus;
    private Instant orderRecievedTimeStamp;
    private Instant orderUpdatedTimeStamp;

    public CustomerOrder(List<ItemByQuantity> items, OrderStatus orderStatus, Instant orderRecievedTimeStamp, Instant orderUpdatedTimeStamp) {
        this.items = items;
        this.orderStatus = orderStatus;
        this.orderRecievedTimeStamp = orderRecievedTimeStamp;
        this.orderUpdatedTimeStamp = orderUpdatedTimeStamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", items=" + items +
                ", orderStatus=" + orderStatus +
                ", orderRecievedTimeStamp=" + orderRecievedTimeStamp +
                ", orderUpdatedTimeStamp=" + orderUpdatedTimeStamp +
                '}';
    }
}
