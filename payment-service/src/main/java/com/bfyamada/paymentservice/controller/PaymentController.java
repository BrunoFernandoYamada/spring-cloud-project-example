package com.bfyamada.paymentservice.controller;

import com.bfyamada.paymentservice.entity.Payment;
import com.bfyamada.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> findPaymentHistoryByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(service.findPaymentHistoryByOrderId(orderId));
    }
}
