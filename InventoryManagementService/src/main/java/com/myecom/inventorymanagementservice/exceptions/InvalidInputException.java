package com.myecom.inventorymanagementservice.exceptions;


import com.myecom.inventorymanagementservice.bean.ItemRequest;
import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {

    private final ItemRequest itemRequest;

    public InvalidInputException(String message, ItemRequest itemRequest) {
        super(message);
        this.itemRequest = itemRequest;
    }

}
