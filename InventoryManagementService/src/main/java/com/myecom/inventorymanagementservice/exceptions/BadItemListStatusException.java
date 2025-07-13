package com.myecom.inventorymanagementservice.exceptions;


import com.myecom.inventorymanagementservice.bean.ItemRequest;
import lombok.Getter;

@Getter
public class BadItemListStatusException extends RuntimeException {

    private final ItemRequest itemRequest;

    public BadItemListStatusException(String message, ItemRequest itemRequest) {
        super(message);
        this.itemRequest = itemRequest;
    }

}
