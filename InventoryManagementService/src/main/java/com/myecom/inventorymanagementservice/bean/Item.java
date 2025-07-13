package com.myecom.inventorymanagementservice.bean;

import com.myecom.inventorymanagementservice.commons.CostUnit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;
    private String itemName;
    private double weightPerUnit;
    private double costPerUnit;
    private CostUnit costUnit;
    private int quantity;
    private Instant itemAdded;
    private Instant itemLastUpdated;

    public Item(String itemName, double weightPerUnit, double costPerUnit, CostUnit costUnit, int quantity, Instant itemAdded, Instant itemLastUpdated) {
        this.itemName = itemName;
        this.weightPerUnit = weightPerUnit;
        this.costPerUnit = costPerUnit;
        this.costUnit = costUnit;
        this.quantity = quantity;
        this.itemAdded = itemAdded;
        this.itemLastUpdated = itemLastUpdated;
    }

    public Item(String itemName, double weightPerUnit, double costPerUnit, CostUnit costUnit, int quantity) {
        this.itemName = itemName;
        this.weightPerUnit = weightPerUnit;
        this.costPerUnit = costPerUnit;
        this.costUnit = costUnit;
        this.quantity = quantity;
    }

    public Item(long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", weightPerUnit=" + weightPerUnit +
                ", costPerUnit=" + costPerUnit +
                ", costUnit=" + costUnit +
                ", quantity=" + quantity +
                ", itemAdded=" + itemAdded +
                ", itemLastUpdated=" + itemLastUpdated +
                '}';
    }
}
