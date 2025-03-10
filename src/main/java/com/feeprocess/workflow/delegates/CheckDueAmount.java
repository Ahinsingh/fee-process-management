package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeprocess.model.Student;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.util.Constants;
import com.feeprocess.util.WorkflowLogger;

@Service("CheckDueAmount")
public class CheckDueAmount implements JavaDelegate {
	
    @Autowired
    private FeeProcessService service;
	
    public static final String VERIFY_STUDENT = "Verify Student";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) {
        WorkflowLogger.info(logger, VERIFY_STUDENT, "Check its a valid student");
        boolean studentDueAmount;

        String studentId = getFormField(execution, Constants.ID);
//        String studentName = getFormField(execution, Constants.NAME);
//        long studentClass = getFormFieldId(execution, Constants.CLASS);
        long studentAmount = getFormFieldId(execution, Constants.AMOUNT);
        
        Student student =  service.student(studentId);

        if (studentId!="" && student!=null && studentAmount != 0 && student.getAmount() > 0) {
        	studentDueAmount = true;
            WorkflowLogger.info(logger, VERIFY_STUDENT, "we can processing the fee");
        } else {
        	studentDueAmount = false;
            WorkflowLogger.error(logger, VERIFY_STUDENT, "we can't processing the fee.Please check the Admin");
        }
        execution.setVariable(Constants.STUDENT_HAS_DUE, studentDueAmount);
    }

    /**
     * @param execution : delegate execution to extract process instance variables
     * @param formField : form field to be extracted
     * @return
     */
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
