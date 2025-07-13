package com.myecom.inventorymanagementservice.exceptions;


import com.myecom.inventorymanagementservice.bean.ItemRequest;
import lombok.Getter;

@Getter
public class OperationFailedException extends RuntimeException {

    private final ItemRequest itemRequest;

    public OperationFailedException(String message, ItemRequest itemRequest) {
        super(message);
        this.itemRequest = itemRequest;
    }

}
