package com.feeprocess.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeprocess.model.FinancicalRecord;
import com.feeprocess.model.Payment;
import com.feeprocess.repository.FinancialRecordRepository;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.util.WorkflowLogger;


@Service
public class UpdateFinancialRecord {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private FinancialRecordRepository finRecordRepository;
    
    public void updateRec(long paymentId, long studentId , String courseCode) {
    	
    	 Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
    	 Payment payment = paymentOpt.get(); 
    	 if (payment == null) {
    	        throw new RuntimeException("Payment record not found for student: " + studentId);
    	    }
    	 FinancicalRecord finRecord = finRecordRepository.findByStudentIdAndCourseCode(studentId,courseCode);
    	 
    	 if (finRecord == null) {
    	        finRecord = new FinancicalRecord();
    	 }
    	 
    	 finRecord.setPaidAmount(payment.getPaidAmount());
    	 finRecord.setPendingAmount(payment.getPendingAmount());
    	 finRecord.setStudentId(payment.getStudentId());
    	 finRecord.setStudentClass(payment.getStudentClass());
    	 finRecord.setStatus(payment.getStatus());
    	 finRecord.setCreatedAt(payment.getCreatedAt());
    	 finRecord.setCourseCode(payment.getCourseCode());
    	 FinancicalRecord res = finRecordRepository.save(finRecord);
    	 
        WorkflowLogger.info(logger, "Financial Record is completed", "Financial Record is completed");
//        execution.setVariable(Constants.FINANCIAL_RECORD_UPDATED, true);
        
    }
    
}