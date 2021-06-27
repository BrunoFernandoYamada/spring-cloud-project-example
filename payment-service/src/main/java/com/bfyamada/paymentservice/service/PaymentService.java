package com.bfyamada.paymentservice.service;

import com.bfyamada.paymentservice.entity.Payment;
import com.bfyamada.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    public Payment savePayment(Payment payment){
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return repo.save(payment);
    }

    public String paymentProcessing(){
        return (new Random().nextBoolean() ?"Success": "Faild");
    }

}
