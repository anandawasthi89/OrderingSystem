package com.myecom.inventorymanagementservice.bean;

import com.myecom.inventorymanagementservice.commons.ItemListStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Long orderId;
    private List<Item> Items;
    private ItemListStatus itemListStatus;
}
