package com.myecom.inventorymanagementservice.service;

import com.myecom.inventorymanagementservice.Repo.ItemsRepo;
import com.myecom.inventorymanagementservice.bean.Item;
import com.myecom.inventorymanagementservice.bean.ItemRequest;
import com.myecom.inventorymanagementservice.bean.ItemResponse;
import com.myecom.inventorymanagementservice.commons.ItemListStatus;
import com.myecom.inventorymanagementservice.exceptions.InvalidInputException;
import com.myecom.inventorymanagementservice.exceptions.MissingItemsException;
import com.myecom.inventorymanagementservice.exceptions.OperationFailedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemManagementService {

    private final ItemsRepo itemsRepo;

    @Autowired
    ItemManagementService(ItemsRepo itemsRepo) {
        this.itemsRepo=itemsRepo;
    }

    @Transactional
    public ItemResponse processNewOrderItemList(ItemRequest itemRequest) {
        ItemListStatus itemListStatus = ItemListStatus.Processed_AllItems;
        List<Item> itemList = itemRequest.getItems().stream().map(requestItem ->{
                    Optional<Item> optionalItem = itemsRepo.findById(requestItem.getItemId());
                    if(optionalItem.isPresent()){
                        Item item1 = optionalItem.get();
                        if(item1.getQuantity() > requestItem.getQuantity()){
                            item1.setQuantity(item1.getQuantity() - requestItem.getQuantity());
                            item1.setItemLastUpdated(Instant.now());
                            return item1;
                        }else{
                            throw new MissingItemsException("Some of the items from the list are not available in the requested quantity. Order not processed.");
                        }
                    }else{
                        //add log that an item not found.
                        throw new MissingItemsException("Items not found in the inventory. Order not processed.");
                    }
                })
                .toList();
        try{
            itemList = itemsRepo.saveAll(itemList);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Invalid data: " + ex.getMostSpecificCause().getMessage(), itemRequest);
        } catch (ConstraintViolationException ex) {
            throw new InvalidInputException("Validation failed: " + ex.getMessage(), itemRequest);
        } catch (TransactionSystemException ex) {
            Throwable rootCause = ex.getRootCause();
            if (rootCause instanceof ConstraintViolationException) {
                throw new InvalidInputException("Transaction failed due to invalid input", itemRequest);
            }
            throw new OperationFailedException("Transaction error", itemRequest);
        } catch (Exception ex) {
            throw new OperationFailedException("Unexpected error during saveAll", itemRequest);
        }

        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(), itemList, itemList, ItemListStatus.Processed_AllItems, "New inventory stored successfully");
        return itemResponse;
    }

    @Transactional
    public ItemResponse processNewInventory(ItemRequest itemRequest) {
        List<Item> itemList = itemRequest.getItems().stream().map(requestItem ->{
                    Optional<Item> optionalItem = itemsRepo.findByItemNameAndCostUnit(requestItem.getItemName(),requestItem.getCostUnit());
                    if(optionalItem.isPresent()){
                        Item item1 = optionalItem.get();
                        item1.setQuantity(item1.getQuantity()+requestItem.getQuantity());
                        item1.setItemLastUpdated(Instant.now());
                        return item1;
                    }else{
                        return new Item(requestItem.getItemName(),
                                requestItem.getWeightPerUnit(),
                                requestItem.getCostPerUnit(),
                                requestItem.getCostUnit(),
                                requestItem.getQuantity(),
                                Instant.now(),
                                null);
                    }
                })
                .toList();
        try{
            itemList = itemsRepo.saveAll(itemList);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Invalid data: " + ex.getMostSpecificCause().getMessage(), itemRequest);
        } catch (ConstraintViolationException ex) {
            throw new InvalidInputException("Validation failed: " + ex.getMessage(), itemRequest);
        } catch (TransactionSystemException ex) {
            Throwable rootCause = ex.getRootCause();
            if (rootCause instanceof ConstraintViolationException) {
                throw new InvalidInputException("Transaction failed due to invalid input", itemRequest);
            }
            throw new OperationFailedException("Transaction error", itemRequest);
        } catch (Exception ex) {
            throw new OperationFailedException("Unexpected error during saveAll", itemRequest);
        }
        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(), itemList, itemList, ItemListStatus.Stored, "New inventory stored successfully");
        return itemResponse;
    }

    public ItemResponse addReturnedItemList(ItemRequest itemRequest) {
        List<Item> itemList = itemRequest.getItems().stream().map(requestItem ->{
                    Optional<Item> optionalItem = itemsRepo.findByItemNameAndCostUnit(requestItem.getItemName(),requestItem.getCostUnit());
                    if(optionalItem.isPresent()){
                        Item item1 = optionalItem.get();
                        item1.setQuantity(item1.getQuantity()+requestItem.getQuantity());
                        item1.setItemLastUpdated(Instant.now());
                        return item1;
                    }else{
                        //Add log that the item should have been present but was not found for some reason.
                        return new Item(requestItem.getItemName(),
                                requestItem.getWeightPerUnit(),
                                requestItem.getCostPerUnit(),
                                requestItem.getCostUnit(),
                                requestItem.getQuantity(),
                                Instant.now(),
                                null);
                    }
                })
                .toList();
        try{
            itemList = itemsRepo.saveAll(itemList);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Invalid data: " + ex.getMostSpecificCause().getMessage(), itemRequest);
        } catch (ConstraintViolationException ex) {
            throw new InvalidInputException("Validation failed: " + ex.getMessage(), itemRequest);
        } catch (TransactionSystemException ex) {
            Throwable rootCause = ex.getRootCause();
            if (rootCause instanceof ConstraintViolationException) {
                throw new InvalidInputException("Transaction failed due to invalid input", itemRequest);
            }
            throw new OperationFailedException("Transaction error", itemRequest);
        } catch (Exception ex) {
            throw new OperationFailedException("Unexpected error during saveAll", itemRequest);
        }
        ItemResponse itemResponse = new ItemResponse(itemRequest.getOrderId(), itemList, itemList, ItemListStatus.Stored, "Returned inventory stored successfully");
        return itemResponse;
    }

    public ItemResponse listAllItems() {
        List<Item> itemList = itemsRepo.findAll();
        ItemResponse itemResponse = new ItemResponse(itemList, null, "Current inventory");
        return itemResponse;
    }

    public ItemResponse isItemAvailable(long itemId) {
        List<Item> itemList = new ArrayList<>();
        Optional<Item> optionalItem = itemsRepo.findById(itemId);
        if (optionalItem.isPresent()){
            itemList.add(optionalItem.get());
        }else {
            throw new InvalidInputException("ItemId "+itemId+" not present", new ItemRequest());
        }
        ItemResponse itemResponse = new ItemResponse(itemList, null, "ItemId "+itemId+" found");
        return itemResponse;
    }
}
