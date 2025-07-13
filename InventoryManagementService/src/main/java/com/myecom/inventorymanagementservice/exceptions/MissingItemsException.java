package com.myecom.inventorymanagementservice.exceptions;


import com.myecom.inventorymanagementservice.bean.Item;
import com.myecom.inventorymanagementservice.bean.ItemRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class MissingItemsException extends RuntimeException {

    public MissingItemsException(String message) {
        super(message);
    }

}
