package com.myecom.oms.util;

import com.myecom.oms.Repo.ItemsRepo;
import com.myecom.oms.bean.ItemByQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemListValidationUtil {

    @Autowired
    private final ItemsRepo itemsRepo;

    @Autowired
    ItemListValidationUtil(ItemsRepo itemsRepo){
        this.itemsRepo = itemsRepo;
    }

    public boolean validate(List<ItemByQuantity> items) {
        for (ItemByQuantity item:items){
            if (item.getItemId()==null) return false;
            if(!itemsRepo.existsById(item.getItemId()) || item.getQuantity() <= 0) return false;
        }
        return true;
    }
}
