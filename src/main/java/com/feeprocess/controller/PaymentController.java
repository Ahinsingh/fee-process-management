package com.feeprocess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeprocess.model.Payment;
import com.feeprocess.service.FeeProcessService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private FeeProcessService service;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = service.createPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
}
