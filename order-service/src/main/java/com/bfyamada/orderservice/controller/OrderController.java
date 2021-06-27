package com.bfyamada.orderservice.controller;

import com.bfyamada.orderservice.dto.TransactionRequest;
import com.bfyamada.orderservice.dto.TransactionResponse;
import com.bfyamada.orderservice.entity.Order;
import com.bfyamada.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    public OrderService service;

    @PostMapping("/order")
    private ResponseEntity<Order> saveOrder(@RequestBody Order order){
        Order obj = service.saveOrder(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/bulkOrder")
    private ResponseEntity<TransactionResponse> bulkOrder(@RequestBody TransactionRequest request){
        return ResponseEntity.ok(service.bulkOrderAndPayment(request));
    }
}
