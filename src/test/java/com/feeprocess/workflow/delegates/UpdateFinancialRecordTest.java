package com.feeprocess.workflow.delegates;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class UpdateFinancialRecordTest {

    private DelegateExecution execution;

    @InjectMocks
    private UpdateFinancialRecord updateFinancialRecord;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        execution = CamundaMockito.delegateExecutionFake();
    }
    
    @Test
    public void testExecute_UpdateFinancialRecord() {
    	Map<String, Object> variables = new HashMap<>();
//        variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
        execution.setVariables(variables);
        updateFinancialRecord.execute(execution);
        assertEquals(true, execution.getVariable(Constants.FINANCIAL_RECORD_UPDATED));
    }
}
