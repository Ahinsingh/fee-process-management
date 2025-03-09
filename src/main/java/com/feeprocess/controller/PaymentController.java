package com.feeprocess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeprocess.model.Payment;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.repository.StudentRepository;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        if (!studentRepository.existsById(payment.getStudentId())) {
            return ResponseEntity.badRequest().body("Student not found!");
        }

        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(savedPayment);
    }
}
