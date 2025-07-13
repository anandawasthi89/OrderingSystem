package com.myecom.oms.bean;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemByQuantity {
    private Long itemId;
    private int quantity;

    @Override
    public String toString() {
        return "ItemByQuantity{" +
                "itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}
