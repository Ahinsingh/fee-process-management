package com.feeprocess.workflow.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.mockito.CamundaMockito;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class SendSuccessEmailTest {

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private SendSuccessEmail sendSuccessEmail;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        execution = CamundaMockito.delegateExecutionFake();
    }
    
    @Test
   public void testExecute_SendSuccessEmail() throws Exception {
    	Map<String, Object> variables = new HashMap<>();
        execution.setVariables(variables);
        sendSuccessEmail.execute(execution);
        assertEquals(true, execution.getVariable(Constants.PAYMENT_DONE));
        assertEquals(true, execution.getVariable(Constants.MAIL_SENT));
    }
}
