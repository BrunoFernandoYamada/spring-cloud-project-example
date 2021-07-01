package com.bfyamada.orderservice.service;

import com.bfyamada.orderservice.dto.Payment;
import com.bfyamada.orderservice.dto.TransactionRequest;
import com.bfyamada.orderservice.dto.TransactionResponse;
import com.bfyamada.orderservice.entity.Order;
import com.bfyamada.orderservice.feignClients.PaymentFeignClient;
import com.bfyamada.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    private Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public Order saveOrder(Order order){
        return repo.save(order);
    }

    public TransactionResponse bulkOrderAndPayment(TransactionRequest request) throws JsonProcessingException {
        TransactionResponse response = new TransactionResponse();

        Order order = request.getOrder();
        Payment payment = request.getPayment();

        if(payment == null){
            payment = new Payment();
        }
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        LOGGER.info("OrderService request : {}", new ObjectMapper().writeValueAsString(request));

        Payment paymentResponse =  paymentFeignClient.doPayment(payment).getBody();

        LOGGER.info("Payment-service response from OrderService Rest call : {}", new ObjectMapper().writeValueAsString(paymentResponse));
        if(paymentResponse.getPaymentStatus().equals("Success")){
            response.setMessage("Payment processing successful and order placed");
        }else{
            response.setMessage("There is a failure in Payment API, Order added to cart");
        }
        this.saveOrder(order);

        response.setOrder(order);
        response.setTransactionId(paymentResponse.getTransactionId());
        response.setAmount(paymentResponse.getAmount());

        return response;


    }
}
