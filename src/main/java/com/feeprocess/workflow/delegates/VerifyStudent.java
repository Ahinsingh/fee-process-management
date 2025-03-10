package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feeprocess.util.Constants;
import com.feeprocess.util.WorkflowLogger;

@Service("VerifyStudent")
public class VerifyStudent implements JavaDelegate {

    public static final String VERIFY_STUDENT = "Verify Student";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) {
        WorkflowLogger.info(logger, VERIFY_STUDENT, "Check its a valid student");
        boolean studentAvailable;
        String studentId = getFormField(execution, Constants.ID);
        String studentName = getFormField(execution, Constants.NAME);
        long studentClass = getFormFieldId(execution, Constants.CLASS);
        long studentAmount = getFormFieldId(execution, Constants.AMOUNT);

        if (studentId !="" && !studentName.isEmpty() && studentClass!=0) {
        	studentAvailable = true;
            WorkflowLogger.info(logger, VERIFY_STUDENT, "we can processing the fee");
        } else {
        	studentAvailable = false;
            WorkflowLogger.error(logger, VERIFY_STUDENT, "we cannot process payement. Student Id and Name is not available.");
        }

        execution.setVariable(Constants.STUDENT_AVAILABLE, studentAvailable);
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
