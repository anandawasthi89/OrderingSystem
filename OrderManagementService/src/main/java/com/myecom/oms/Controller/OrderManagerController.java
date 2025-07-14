package com.myecom.oms.Controller;

import com.myecom.oms.bean.OrderRequest;
import com.myecom.oms.bean.OrderResponse;
import com.myecom.oms.commons.OrderStatus;
import com.myecom.oms.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class OrderManagerController {

    private final OrderManagerService orderManagerService;

    @Autowired
    OrderManagerController(OrderManagerService orderManagerService){
        this.orderManagerService = orderManagerService;
    }

    @PostMapping(path = "/neworder")
    public Mono<OrderResponse> newOrder(@RequestBody OrderRequest orderRequest){
        return orderManagerService.addNewOrder(orderRequest);
    }

    @GetMapping(path = "/listallorders")
    public Mono<OrderResponse> listAllOrders(){
        return orderManagerService.listAllOrders();
    }

    @GetMapping(path = "/getorder")
    public Mono<OrderResponse> getOrder(@PathVariable Long orderId){
        return orderManagerService.getOrder(orderId);
    }

    @PutMapping(path = "/updateorder")
    public Mono<OrderResponse> updateStatus(@RequestParam(name = "orderId") Long orderId, @RequestParam(name = "status") OrderStatus orderStatus){
        return orderManagerService.updateStatus(orderId, orderStatus);
    }

}
