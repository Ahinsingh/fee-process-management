package com.feeprocess.workflow.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.mockito.CamundaMockito;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class VerifyStudentTest {

    private DelegateExecution execution;

    @InjectMocks
    private VerifyStudent verifyStudent;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        execution = CamundaMockito.delegateExecutionFake();
    }

    @Test
    public void testExecute_StudentAvailable() {     
        Map<String, Object> variables = new HashMap<>();
        variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
        variables.put(Constants.NAME, "Ahin");
        variables.put(Constants.CLASS, 12L);
        variables.put(Constants.AMOUNT, 1000L);
        execution.setVariables(variables);
        verifyStudent.execute(execution);
        assertEquals(true, execution.getVariable(Constants.STUDENT_AVAILABLE));
    }
}
