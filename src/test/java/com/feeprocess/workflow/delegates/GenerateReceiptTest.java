package com.feeprocess.workflow.delegates;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

import com.feeprocess.model.Payment;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.workflow.delegates.GenerateReceipt;
import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class GenerateReceiptTest {

    @Mock
    private DelegateExecution execution;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private GenerateReceipt generateReceipt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        execution = CamundaMockito.delegateExecutionFake();
    }


    @Test
    void testExecute_GenerateReceipt_Success() {
        Payment payment = new Payment("67cd2d31dd019866133eb08c", "67cd2c4fc1b84829024da64e", 10, 1200, true);
        when(paymentRepository.findByStudentId("67cd2c4fc1b84829024da64e")).thenReturn(payment);
        Map<String, Object> variables = new HashMap<>();
        variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
        variables.put(Constants.NAME, "Ahin");
        variables.put(Constants.CLASS, 12L);
        variables.put(Constants.AMOUNT, 1000L);
        execution.setVariables(variables);
        generateReceipt.execute(execution);
        Assertions.assertEquals(true, execution.getVariable(Constants.RECEIPT_GENERATED));
    }

    @Test
    void testExecute_GenerateReceipt_Failure() {
        Map<String, Object> variables = new HashMap<>();
        execution.setVariables(variables);
        generateReceipt.execute(execution);
//        verify(execution).setVariable(Constants.RECEIPT_GENERATED, false);
        Assertions.assertEquals(false, execution.getVariable(Constants.RECEIPT_GENERATED));
    }

}
