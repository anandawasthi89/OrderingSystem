package com.myecom.inventorymanagementservice.exceptions;

import com.myecom.inventorymanagementservice.bean.ItemRequest;
import com.myecom.inventorymanagementservice.bean.ItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingItemsException.class)
    public ResponseEntity<ItemResponse> handleMissingItemsException(MissingItemsException ex){
        ItemResponse itemResponse = new ItemResponse(null,null, ex.getMessage());
        return ResponseEntity.badRequest().body(itemResponse);
    }

    @ExceptionHandler(BadItemListStatusException.class)
    public ResponseEntity<ItemResponse> handleBadItemListStatusException(BadItemListStatusException ex){
        ItemRequest itemRequest = ex.getItemRequest();
        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(),itemRequest.getItems(),null,itemRequest.getItemListStatus(), ex.getMessage());
        return ResponseEntity.badRequest().body(itemResponse);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ItemResponse> handleInvalidInputException(InvalidInputException ex){
        ItemRequest itemRequest = ex.getItemRequest();
        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(),itemRequest.getItems(),null, itemRequest.getItemListStatus(), ex.getMessage());
        return ResponseEntity.badRequest().body(itemResponse);
    }

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ItemResponse> handleOperationFailedException(OperationFailedException ex){
        ItemRequest itemRequest = ex.getItemRequest();
        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(),itemRequest.getItems(),null,itemRequest.getItemListStatus(), ex.getMessage());
        return ResponseEntity.badRequest().body(itemResponse);
    }
}
