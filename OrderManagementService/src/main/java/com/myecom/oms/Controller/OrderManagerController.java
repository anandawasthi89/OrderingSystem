package com.myecom.oms.Controller;

import com.myecom.oms.bean.ItemResponse;
import com.myecom.oms.bean.OrderRequest;
import com.myecom.oms.bean.OrderResponse;
import com.myecom.oms.commons.OrderStatus;
import com.myecom.oms.service.ItemManagementService;
import com.myecom.oms.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderManagerController {

    private final OrderManagerService orderManagerService;
    private final ItemManagementService itemManagementService;

    @Autowired
    OrderManagerController(OrderManagerService orderManagerService, ItemManagementService itemManagementService){
        this.orderManagerService = orderManagerService;
        this.itemManagementService = itemManagementService;
    }

    @PostMapping(path = "/neworder")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody OrderRequest orderRequest){
        ResponseEntity<OrderResponse> result = orderManagerService.addNewOrder(orderRequest);
        return result;
    }

    @GetMapping(path = "/listallitems")
    public ResponseEntity<ItemResponse> listAllItems(){
        ResponseEntity<ItemResponse> result = itemManagementService.listAllItems();
        return result;
    }

    @GetMapping(path = "/listallorders")
    public ResponseEntity<OrderResponse> listAllOrders(){
        ResponseEntity<OrderResponse> result = orderManagerService.listAllOrders();
        return result;
    }

    @GetMapping(path = "/getorder")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId){
        ResponseEntity<OrderResponse> result = orderManagerService.getOrder(orderId);
        return result;
    }

    @PutMapping(path = "/updateorder")
    public ResponseEntity<OrderResponse> updateStatus(@RequestParam(name = "orderId") Long orderId, @RequestParam(name = "status") OrderStatus orderStatus){
        ResponseEntity<OrderResponse> result = orderManagerService.updateStatus(orderId, orderStatus);
        return result;
    }

}
