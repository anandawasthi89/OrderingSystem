package com.myecom.inventorymanagementservice.Repo;

import com.myecom.inventorymanagementservice.bean.Item;
import com.myecom.inventorymanagementservice.commons.CostUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepo extends JpaRepository<Item,Long> {
    Optional<Item> findByItemNameAndCostUnit(String itemName, CostUnit costUnit);
}
