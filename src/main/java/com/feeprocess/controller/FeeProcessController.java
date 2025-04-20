package com.feeprocess.controller;


import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feeprocess.dto.PaymentDTO;
import com.feeprocess.model.Payment;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.serviceImpl.PaymentProcess;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@RequestMapping("/fee")
public class FeeProcessController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    PaymentProcess paymentProcess;
    
    @Autowired
    private FeeProcessService service;
    
    @GetMapping("/today")
    public ResponseEntity<List<Payment>> getPaymentsToday() {
        List<Payment> payments = service.getPaymentsToday();
        return payments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(payments);
    }

    @GetMapping("/yesterday")
    public ResponseEntity<List<Payment>> getPaymentsYesterday() {
        List<Payment> payments = service.getPaymentsYesterday();
        return payments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(payments);
    }

    @GetMapping("/last-year")
    public ResponseEntity<List<Payment>> getPaymentsLastYear() {
        List<Payment> payments = service.getPaymentsLastYear();
        return payments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(payments);
    }

    @PostMapping("/process")
    @CircuitBreaker(name = "fee-service", fallbackMethod = "fallbackFeeProcess")
    public ResponseEntity<String> startProcess(@RequestBody PaymentDTO paymentRequest) {
    	boolean validAmt = false;
    	boolean validStudent =  paymentProcess.verifyStudent(paymentRequest.getStudentId(), paymentRequest.getStudentClass());
    	if(validStudent) {
    		validAmt =  paymentProcess.checkDueAmount(paymentRequest.getStudentId(), paymentRequest.getAmount());
    	}
    	Payment payment= null;
    	try {
	    	if(validAmt) {
	    		payment = paymentProcess.executePayment(paymentRequest);
	    	    return ResponseEntity
	                    .status(HttpStatus.CREATED)
	                    .body("Payment processed successfully with ID: " + payment.getId());
	    	}else {
	    		return ResponseEntity
	                    .status(HttpStatus.METHOD_NOT_ALLOWED)
	                    .body("Failed to process payment. Please try again.");
	    	}
    	} catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process payment. Please try again.");
        }
    }
    
    public ResponseEntity<String> fallbackFeeProcess(PaymentDTO paymentRequest, Throwable ex) {
    	return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Payment service is temporarily unavailable. Please try again later.");
    }
    
    @GetMapping("/receipts")
    @Retry(name = "feeService", fallbackMethod = "fallbackGetAllReceipts")
    public ResponseEntity<List<Payment>> getAllReceipts() {
        return ResponseEntity.ok(service.findAll());
    }

    public ResponseEntity<List<Payment>> fallbackGetAllReceipts(Throwable ex) {
        return ResponseEntity.status(503).body(Collections.emptyList());
    }

    @GetMapping("/receipt/{id}")
    @Retry(name = "feeService", fallbackMethod = "fallbackGetReceipt")
    public ResponseEntity<Payment> getReceipt(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    public ResponseEntity<Payment> fallbackGetReceipt(Throwable ex) {
        return ResponseEntity.status(503).body(null);
    }

}
