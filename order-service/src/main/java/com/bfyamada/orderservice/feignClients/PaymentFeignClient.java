package com.bfyamada.orderservice.feignClients;

import com.bfyamada.orderservice.dto.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "payment-service", path = "/payments")
public interface PaymentFeignClient {

    @PostMapping("/doPayment")
    ResponseEntity<Payment> doPayment(@RequestBody Payment payment);

}
