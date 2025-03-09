package com.feeprocess.workflow.delegates;

import static org.mockito.Mockito.verify;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class SendFailedEmailTest {

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private SendFailedEmail sendFailedEmail;

    @Test
    void testExecute_SendFailedEmail() {
        sendFailedEmail.execute(execution);

        verify(execution).setVariable(Constants.PAYMENT_DONE, false);
        verify(execution).setVariable(Constants.MAIL_SENT, true);
    }
}
