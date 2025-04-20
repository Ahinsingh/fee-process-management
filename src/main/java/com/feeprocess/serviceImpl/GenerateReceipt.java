package com.feeprocess.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.util.GenerateReceiptUtil;
import com.feeprocess.util.WorkflowLogger;

@Service
public class GenerateReceipt{

	public static final String STEP = "STEP ";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentRepository paymentRepository;
	
	 @Autowired
	 private FeeProcessService service;
	 
	public void generateReceipt(long studentId, String studentClass) {

		if (studentId != 0) {
			
			Optional<Payment> payment = paymentRepository
				    .findTopByStudentIdOrderByCreatedAtDesc(studentId, PageRequest.of(0, 1))
				    .stream()
				    .findFirst();
			
//			Optional<Payment> payment = paymentRepository.findTopByStudentIdOrderByCreatedAtDesc(studentId);
			WorkflowLogger.info(logger, "Prepare PDF File", "Follow below to make PDF file");
			Student student =  service.student(studentId);
			
			GenerateReceiptUtil.generateReceipt(student, payment.get());
			
			WorkflowLogger.info(logger, STEP + 1,
					"Do all the configurations are fetch the record from DB to generate recipt.");
			WorkflowLogger.info(logger, "Generating Recipt", "You will get the recipt via mail...");
		} else {
			WorkflowLogger.info(logger, "Recipt Not Generate", "Requried values are not available");
		}
	}


}
