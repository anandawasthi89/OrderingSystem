package com.myecom.oms.bean;

import com.myecom.oms.commons.CostUnit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;
    private String itemName;
    private double weight;
    private double cost;
    private CostUnit costUnit;

    public Item(String itemName, double weight, double cost, CostUnit costUnit) {
        this.itemName = itemName;
        this.weight = weight;
        this.cost = cost;
        this.costUnit = costUnit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", weight=" + weight +
                ", cost=" + cost +
                ", costUnit=" + costUnit +
                '}';
    }
}
