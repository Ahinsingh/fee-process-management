package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feeprocess.util.Constants;
import com.feeprocess.util.WorkflowLogger;

@Service("SendFailedEmail")
public class SendFailedEmail implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) {

        WorkflowLogger.info(logger, "Send Failed Email", "Payment is failed due to technical issue....");
        execution.setVariable(Constants.PAYMENT_DONE, false);

        WorkflowLogger.info(logger, "Send Failed Email", "Payment is failed. We sent the mail to your mailId");
        execution.setVariable(Constants.MAIL_SENT, true);
    }
}
