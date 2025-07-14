package com.myecom.oms.bean;

import com.myecom.oms.commons.ItemListStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Long orderId;
    private List<Item> originalItems;
    private List<Item> responseItems;
    private ItemListStatus itemListStatus;
    private String message;

    public ItemResponse(List<Item> responseItems, ItemListStatus itemListStatus, String message) {
        this.responseItems = responseItems;
        this.itemListStatus = itemListStatus;
        this.message = message;
    }
}
