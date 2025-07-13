package com.myecom.oms.Repo;

import com.myecom.oms.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepo extends JpaRepository<Item,Long> {
}
