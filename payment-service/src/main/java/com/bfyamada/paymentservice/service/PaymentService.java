package com.bfyamada.paymentservice.service;

import com.bfyamada.paymentservice.entity.Payment;
import com.bfyamada.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    private Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public Payment savePayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        LOGGER.info("PaymentService request : {}", new ObjectMapper().writeValueAsString(payment));
        return repo.save(payment);
    }

    public String paymentProcessing(){
        return (new Random().nextBoolean() ?"Success": "Faild");
    }

    public Payment findPaymentHistoryByOrderId(Long orderId) throws JsonProcessingException {
        Payment payment = repo.findByOrderId(orderId);
        LOGGER.info("PaymentService request : {}", new ObjectMapper().writeValueAsString(payment));
        return repo.findByOrderId(orderId);
    }
}
