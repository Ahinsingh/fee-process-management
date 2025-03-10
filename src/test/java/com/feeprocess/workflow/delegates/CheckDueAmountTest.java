package com.feeprocess.workflow.delegates;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.mockito.CamundaMockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feeprocess.model.Student;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class CheckDueAmountTest {

    @Mock
    private DelegateExecution execution;

    @Mock
    private FeeProcessService feeProcessService;

    @InjectMocks
    private CheckDueAmount checkDueAmount;

    
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        execution = CamundaMockito.delegateExecutionFake();

    }

    @Test
    void testExecute_StudentHasDue() {
        Student student = new Student("67cd2c4fc1b84829024da64e", "Ahin", 10, 1000);
        when(feeProcessService.student("67cd2c4fc1b84829024da64e")).thenReturn(student);
        Map<String, Object> variables = new HashMap<>();
        variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
        variables.put(Constants.AMOUNT, 1000L);
        execution.setVariables(variables);
        checkDueAmount.execute(execution);
        Assertions.assertEquals(true, execution.getVariable(Constants.STUDENT_HAS_DUE));
    }

    @Test
    void testExecute_StudentNoDue() {
        Student student = new Student("67cd2c4fc1b84829024da64e", "Ahin", 10, 0);
        when(feeProcessService.student("67cd2c4fc1b84829024da64e")).thenReturn(student);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
        variables.put(Constants.AMOUNT, 0L);
        execution.setVariables(variables);
        checkDueAmount.execute(execution);
        Assertions.assertEquals(false, execution.getVariable(Constants.STUDENT_HAS_DUE));
//        verify(execution).setVariable(Constants.STUDENT_HAS_DUE, false);
    }
}
