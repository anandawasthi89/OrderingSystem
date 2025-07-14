package com.myecom.oms.service;

import com.myecom.oms.Repo.CustomerOrderRepo;
import com.myecom.oms.bean.*;
import com.myecom.oms.commons.ItemListStatus;
import com.myecom.oms.commons.OrderStatus;
import com.myecom.oms.config.WebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderManagerService {

    private final CustomerOrderRepo customerOrderRepo;
    private final WebClientConfig webClientConfig;

    @Autowired
    OrderManagerService(CustomerOrderRepo customerOrderRepo, WebClientConfig webClientConfig){
        this.customerOrderRepo=customerOrderRepo;
        this.webClientConfig=webClientConfig;
    }

    public Mono<OrderResponse> addNewOrder(OrderRequest orderRequest) {
        CustomerOrder customerOrder = customerOrderRepo.createNewOrder(orderRequest);
        ItemRequest itemRequest = new ItemRequest(customerOrder.getOrderId(),customerOrder.getItems(), ItemListStatus.NewOrder);
        return webClientConfig.getWebClient()
                .post()
                .uri("/neworder")
                .bodyValue(itemRequest)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.toEntity(ItemResponse.class)
                                .flatMap(entity -> {
                                    ItemResponse itemResponse = entity.getBody();
                                    customerOrder.setOrderStatus(
                                            itemResponse.getItemListStatus() == ItemListStatus.Processed_AllItems
                                                    ? OrderStatus.Processed
                                                    : OrderStatus.Failed
                                    );
                                    customerOrderRepo.updateOrder(customerOrder.getOrderId(), customerOrder);
                                    List<CustomerOrder> responseList = List.of(customerOrder);
                                    String message = customerOrder.getOrderStatus() == OrderStatus.Processed
                                            ? "Order processed successfully"
                                            : "Order failed.";
                                    return Mono.just(new OrderResponse(responseList, message));
                                });
                    } else {
                        // Handle non-200 status
                        customerOrder.setOrderStatus(OrderStatus.Failed);
                        customerOrderRepo.updateOrder(customerOrder.getOrderId(), customerOrder);
                        List<CustomerOrder> responseList = List.of(customerOrder);
                        return Mono.just(new OrderResponse(responseList, "Order failed: " + response.statusCode()));
                    }
                })
                .onErrorResume(throwable -> {
                    customerOrder.setOrderStatus(OrderStatus.Failed);
                    customerOrderRepo.updateOrder(customerOrder.getOrderId(), customerOrder);
                    List<CustomerOrder> responseList = List.of(customerOrder);
                    return Mono.just(new OrderResponse(responseList, "Order failed due to exception: " + throwable.getMessage()));
                });

    }

    public Mono<OrderResponse> listAllOrders() {
        return customerOrderRepo.listAllOrders().flatMap(customerOrders -> Mono.just(new OrderResponse(customerOrders,"Complete list of orders.")));
    }

    public Mono<OrderResponse> getOrder(Long orderId) {
        return customerOrderRepo.getOrder(orderId).flatMap(customerOrder -> {
            List<CustomerOrder> customerOrderList = new ArrayList<>();
            customerOrderList.add(customerOrder);
            return Mono.just(new OrderResponse(customerOrderList,"Order retrieved."));
        });
    }

    public Mono<OrderResponse> updateStatus(Long orderId, OrderStatus orderStatus) {
        return customerOrderRepo.getOrder(orderId).flatMap(customerOrder -> {
            customerOrder.setOrderStatus(orderStatus);
            List<CustomerOrder> customerOrderList = new ArrayList<>();
            customerOrderList.add(customerOrderRepo.updateOrder(orderId,customerOrder));
            return Mono.just(new OrderResponse(customerOrderList,"updated the status for the order."));
        });
    }
}
