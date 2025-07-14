package com.myecom.oms.Repo;

import com.myecom.oms.bean.CustomerOrder;
import com.myecom.oms.bean.OrderRequest;
import com.myecom.oms.commons.OrderStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerOrderRepo {
    AtomicLong OrderId = new AtomicLong(1);
    Map<Long, CustomerOrder> OrderDB = new ConcurrentHashMap<>();

    public CustomerOrder createNewOrder(OrderRequest orderRequest) {
        Long orderId = getNewOrderId();
        CustomerOrder customerOrder = new CustomerOrder(orderId,orderRequest.getItems(), OrderStatus.Recieved, Instant.now(),null);
        OrderDB.put(orderId,customerOrder);
        return customerOrder;
    }

    public CustomerOrder updateOrder(Long orderId, CustomerOrder customerOrder) {
        OrderDB.put(orderId,customerOrder);
        return customerOrder;
    }

    public Mono<List<CustomerOrder>> listAllOrders() {
        return Mono.just(OrderDB.values().stream().toList());
    }

    public Mono<CustomerOrder> getOrder(Long orderId) {
        return Mono.just(OrderDB.get(orderId));
    }

    private Long getNewOrderId() {
        return OrderId.incrementAndGet();
    }


}
