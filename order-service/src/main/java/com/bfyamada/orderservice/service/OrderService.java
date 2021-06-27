package com.bfyamada.orderservice.service;

import com.bfyamada.orderservice.dto.Payment;
import com.bfyamada.orderservice.dto.TransactionRequest;
import com.bfyamada.orderservice.dto.TransactionResponse;
import com.bfyamada.orderservice.entity.Order;
import com.bfyamada.orderservice.feignClients.PaymentFeignClient;
import com.bfyamada.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    public Order saveOrder(Order order){
        return repo.save(order);
    }

    public TransactionResponse bulkOrderAndPayment(TransactionRequest request){
        TransactionResponse response = new TransactionResponse();

        Order order = request.getOrder();
        Payment payment = request.getPayment();

        if(payment == null){
            payment = new Payment();
        }
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse =  paymentFeignClient.doPayment(payment).getBody();

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
