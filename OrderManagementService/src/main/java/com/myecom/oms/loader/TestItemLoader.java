package com.myecom.oms.loader;

import com.myecom.oms.Repo.ItemsRepo;
import com.myecom.oms.bean.Item;
import com.myecom.oms.commons.CostUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestItemLoader implements CommandLineRunner {

    @Autowired
    private final ItemsRepo itemsRepo;

    public TestItemLoader(ItemsRepo itemsRepo) {
        this.itemsRepo = itemsRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Item i = itemsRepo.save(new Item("Steel Rod", 12.5, 150.0, CostUnit.kg));
        itemsRepo.save(new Item("Aluminium Sheet", 8.0, 90.0, CostUnit.kg));
        itemsRepo.save(new Item("Copper Wire", 5.5, 200.0, CostUnit.unit));
        itemsRepo.save(new Item("Iron Nail", 0.05, 0.2, CostUnit.unit));
        itemsRepo.save(new Item("Plastic Pipe", 3.2, 45.0, CostUnit.unit));
    }
}
