package com.myecom.inventorymanagementservice.Controller;

import com.myecom.inventorymanagementservice.bean.ItemRequest;
import com.myecom.inventorymanagementservice.bean.ItemResponse;
import com.myecom.inventorymanagementservice.commons.ItemListStatus;
import com.myecom.inventorymanagementservice.exceptions.BadItemListStatusException;
import com.myecom.inventorymanagementservice.service.ItemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryManagerController {

    private final ItemManagementService itemManagementService;

    @Autowired
    InventoryManagerController(ItemManagementService itemManagementService){
        this.itemManagementService = itemManagementService;
    }

    @PostMapping(path = "/neworder")
    public ResponseEntity<ItemResponse> newOrderItemList(@RequestBody ItemRequest itemRequest){
        if(itemRequest.getItemListStatus() != ItemListStatus.NewOrder){
            throw new BadItemListStatusException("For new order, ItemListStatus should be "+ItemListStatus.NewOrder, itemRequest);
        }
        ItemResponse itemResponse = itemManagementService.processNewOrderItemList(itemRequest);
        return ResponseEntity.ok(itemResponse);
    }

    @PostMapping(path = "/newinventory")
    public ResponseEntity<ItemResponse> newInventoryList(@RequestBody ItemRequest itemRequest){
        if(itemRequest.getItemListStatus() != ItemListStatus.NewInventory){
            throw new BadItemListStatusException("For new inventory, ItemListStatus should be "+ItemListStatus.NewInventory, itemRequest);
        }
        ItemResponse itemResponse = itemManagementService.processNewInventory(itemRequest);
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping(path = "/listallitems")
    public ResponseEntity<ItemResponse> listAllItems(){
        ItemResponse itemResponse = itemManagementService.listAllItems();
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping(path = "/itemavailable")
    public ResponseEntity<ItemResponse> isItemAvailable(@PathVariable long itemId){
        ItemResponse itemResponse = itemManagementService.isItemAvailable(itemId);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping(path = "/addreturnedinventory")
    public ResponseEntity<ItemResponse> returnedItemList(@RequestBody ItemRequest itemRequest){
        if(itemRequest.getItemListStatus() != ItemListStatus.NewInventory){
            throw new BadItemListStatusException("For returned items, ItemListStatus should be "+ItemListStatus.ReturnedInventory, itemRequest);
        }
        ItemResponse itemResponse = itemManagementService.addReturnedItemList(itemRequest);
        return ResponseEntity.ok(itemResponse);
    }

}
