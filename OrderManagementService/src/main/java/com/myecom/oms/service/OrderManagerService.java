package com.myecom.oms.service;

import com.myecom.oms.Repo.OrderRepo;
import com.myecom.oms.bean.CustomerOrder;
import com.myecom.oms.bean.OrderRequest;
import com.myecom.oms.bean.OrderResponse;
import com.myecom.oms.commons.OrderStatus;
import com.myecom.oms.util.ItemListValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderManagerService {

    private final OrderRepo orderRepo;
    private final ItemListValidationUtil itemListValidationUtil;

    @Autowired
    OrderManagerService(OrderRepo orderRepo, ItemListValidationUtil itemListValidationUtil){
        this.orderRepo=orderRepo;
        this.itemListValidationUtil = itemListValidationUtil;
    }

    public ResponseEntity<OrderResponse> addNewOrder(OrderRequest orderRequest) {
        ResponseEntity<OrderResponse> response = null;
        if(itemListValidationUtil.validate(orderRequest.getItems())) {
            CustomerOrder order = new CustomerOrder(orderRequest.getItems(), OrderStatus.Recieved, Instant.now(),null);
            List<CustomerOrder> list = new ArrayList<>();
            list.add(orderRepo.save(order));
            OrderResponse orderResponse = new OrderResponse(list,"Successfully Added");
            response = ResponseEntity.ok(orderResponse);
        }else{
            OrderResponse orderResponse = new OrderResponse(null,"Order cannot be added");
            response = ResponseEntity.badRequest().body(orderResponse);
        }
        return response;
    }

    public ResponseEntity<OrderResponse> listAllOrders() {
        List<CustomerOrder> list = orderRepo.findAll();
        OrderResponse orderResponse = new OrderResponse(list,"Found the list of orders");
        return ResponseEntity.ok(orderResponse);
    }

    public ResponseEntity<OrderResponse> getOrder(Long orderId) {
        Optional<CustomerOrder> order = orderRepo.findById(orderId);
        if (order.isPresent()){
            List<CustomerOrder> list = new ArrayList<>();
            list.add(order.get());
            OrderResponse orderResponse = new OrderResponse(list,"Found the order");
            return ResponseEntity.ok(orderResponse);
        }else{
            OrderResponse orderResponse = new OrderResponse(null,"Order not found");
            return ResponseEntity.badRequest().body(orderResponse);
        }
    }

    public ResponseEntity<OrderResponse> updateStatus(Long orderId, OrderStatus orderStatus) {
        Optional<CustomerOrder> order = orderRepo.findById(orderId);
        if(order.isPresent()){
            CustomerOrder actualOrder = order.get();
            actualOrder.setOrderStatus(orderStatus);
            CustomerOrder result  =  orderRepo.save(actualOrder);
            List<CustomerOrder> list = new ArrayList<>();
            list.add(result);
            OrderResponse orderResponse = new OrderResponse(list,"Found the order");
            return ResponseEntity.ok(orderResponse);
        }else {
            OrderResponse orderResponse = new OrderResponse(null,"Order not found");
            return ResponseEntity.badRequest().body(orderResponse);
        }
    }
}
