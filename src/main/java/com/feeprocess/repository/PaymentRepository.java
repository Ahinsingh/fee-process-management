package com.feeprocess.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.feeprocess.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Payment findByStudentId(String studentId);
}
