package com.myecom.oms.bean;

import com.myecom.oms.commons.ItemListStatus;
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
