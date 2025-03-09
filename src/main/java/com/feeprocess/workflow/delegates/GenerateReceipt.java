package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeprocess.model.Payment;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.util.Constants;
import com.feeprocess.util.GenerateReceiptUtil;
import com.feeprocess.util.WorkflowLogger;

@Service("GenerateReceipt")
public class GenerateReceipt implements JavaDelegate {

	public static final String STEP = "STEP ";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public void execute(DelegateExecution execution) {

		String studentId = getFormField(execution, Constants.ID);
		String studentName = getFormField(execution, Constants.NAME);
		long studentClass = getFormFieldId(execution, Constants.CLASS);
		long studentAmount = getFormFieldId(execution, Constants.AMOUNT);

		if (studentId != "" && studentName != "" && studentAmount != 0) {
			Payment payment = paymentRepository.findByStudentId(studentId);
			WorkflowLogger.info(logger, "Prepare PDF File", "Follow below to make PDF file");
			GenerateReceiptUtil.generateReceipt(studentId, studentName, studentClass, payment.getId(),
					payment.getDueAmount());
			WorkflowLogger.info(logger, STEP + 1,
					"Do all the configurations are fetch the record from DB to generate recipt.");
			WorkflowLogger.info(logger, "Generating Recipt", "You will get the recipt via mail...");
			execution.setVariable(Constants.RECEIPT_GENERATED, true);
		} else {
			WorkflowLogger.info(logger, "Recipt Not Generate", "Requried values are not available");
			execution.setVariable(Constants.RECEIPT_GENERATED, false);
		}
	}

	private String getFormField(DelegateExecution execution, String formField) {
		if (execution.hasVariable(formField))
			return (String) execution.getVariable(formField);
		else
			return "";
	}

	private long getFormFieldId(DelegateExecution execution, String formField) {
		if (execution.hasVariable(formField))
			return (long) execution.getVariable(formField);
		else
			return 0;
	}

}
