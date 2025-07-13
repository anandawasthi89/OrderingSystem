package com.myecom.oms.service;

import com.myecom.oms.Repo.ItemsRepo;
import com.myecom.oms.bean.Item;
import com.myecom.oms.bean.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemManagementService {

    private final ItemsRepo itemsRepo;

    @Autowired
    ItemManagementService(ItemsRepo itemsRepo){
        this.itemsRepo=itemsRepo;
    }

    public ResponseEntity<ItemResponse> listAllItems() {
        List<Item> list = itemsRepo.findAll();
        System.out.println(list);
        ItemResponse itemResponse = new ItemResponse(list,"Found the list of items");
        return ResponseEntity.ok(itemResponse);
    }
}
