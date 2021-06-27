package com.bfyamada.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long paymentId;
    private String paymentStatus;
    private String transactionId;
    private Long orderId;
    private double amount;
}
