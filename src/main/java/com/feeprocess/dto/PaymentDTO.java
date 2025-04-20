package com.feeprocess.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private long id;
    private long studentId;
    private String paymentMode;
    private String transactionId;
    private String studentClass;
    private String courseCode;
    private long courseId;
    private double paidAmount;
    private double amount;
    private double pendingAmount;
    private String status;
    private boolean isFullyPaid;
    @CreatedDate
    private LocalDateTime createdAt;
 

}
