package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.util.Constants;
import com.feeprocess.util.WorkflowLogger;

@Service("PaymentProcess")
public class PaymentProcess implements JavaDelegate {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private FeeProcessService service;

    public static final String CHECK_PAYMENT_DONE = "Check Payment Done";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) {
        WorkflowLogger.info(logger, CHECK_PAYMENT_DONE, "Check Payment Done");
        boolean paymentSuccess = false;

        String studentId = getFormField(execution, Constants.ID);
        String studentName = getFormField(execution, Constants.NAME);
        long studentClass = getFormFieldId(execution, Constants.CLASS);
        long studentAmount = getFormFieldId(execution, Constants.AMOUNT);
        
        if (studentId !="" && !studentName.isEmpty() && studentClass!= 0) {
        	 Student student =  service.student(studentId);
        	 
        	 
        	  if(studentAmount == student.getAmount()) {
        		  Payment payment =  new Payment(studentId, studentId, studentClass, studentAmount, true);
        		  Payment savedPayment = paymentRepository.save(payment);
        		  if(savedPayment!=null) {
        			  student.setAmount(0);
        			  Student responseObj =  service.resetStudentFee(student);
        			  paymentSuccess=true;
        		  }
                  WorkflowLogger.info(logger, "Verify_Payment", "Payment Success");
		        }else {
		        	paymentSuccess = false;
		            WorkflowLogger.error(logger, "Verify_Payment", "Payment Failed");
		        }
        } else {
        	paymentSuccess = false;
            WorkflowLogger.error(logger, "Verify_Payment", "Payment Failed");
        }
        execution.setVariable(Constants.PAYMENT_SUCCESS, paymentSuccess);
    }

    private String getFormField(DelegateExecution execution, String formField) {
        if (execution.hasVariable(formField))
            return (String) execution.getVariable(formField);
        else return "";
    }
    
    private long getFormFieldId(DelegateExecution execution, String formField) {
        if (execution.hasVariable(formField))
            return (long) execution.getVariable(formField);
        else return 0;
    }

}
