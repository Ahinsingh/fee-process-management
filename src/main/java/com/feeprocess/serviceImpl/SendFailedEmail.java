package com.feeprocess.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feeprocess.util.WorkflowLogger;

@Service
public class SendFailedEmail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendNotification() {

        WorkflowLogger.info(logger, "Send Failed Email", "Payment is failed due to technical issue....");
//        execution.setVariable(Constants.PAYMENT_DONE, false);

        WorkflowLogger.info(logger, "Send Failed Email", "Payment is failed. We sent the mail to your mailId");
//        execution.setVariable(Constants.MAIL_SENT, true);
        
    }
}
