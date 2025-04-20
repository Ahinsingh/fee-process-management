package com.feeprocess.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feeprocess.util.WorkflowLogger;

@Service
public class SendSuccessEmail {
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
    public void sendNotificationMail() throws Exception {
        WorkflowLogger.info(logger, "Send Failed Email", "Payment is successfully done....");
//        execution.setVariable(Constants.PAYMENT_DONE, true);

        WorkflowLogger.info(logger, "Send Failed Email", "Payment is Approved. We sent the mail to your mailId");
//        execution.setVariable(Constants.MAIL_SENT, true);
    }
}
