package com.bfyamada.paymentservice.controller;

import com.bfyamada.paymentservice.entity.Payment;
import com.bfyamada.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/doPayment")
    public ResponseEntity<Payment> doPayment(@RequestBody Payment payment){
        Payment obj = service.savePayment(payment);
        return ResponseEntity.ok(obj);
    }
}
