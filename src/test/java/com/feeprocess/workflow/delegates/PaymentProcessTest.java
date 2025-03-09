package com.feeprocess.workflow.delegates;

import static org.mockito.ArgumentMatchers.any;
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

import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.repository.StudentRepository;
import com.feeprocess.util.Constants;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessTest {

    @Mock
    private DelegateExecution execution;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PaymentProcess paymentProcess;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        execution = CamundaMockito.delegateExecutionFake();
    }

    @Test
    void testExecute_PaymentSuccess() {
    	 Student student = new Student("67cd2c4fc1b84829024da64e", "Ahin", 10, 1000);
         when(studentRepository.findById("67cd2c4fc1b84829024da64e")).thenReturn(Optional.of(student));
         
         Map<String, Object> variables = new HashMap<>();
         variables.put(Constants.ID, "67cd2c4fc1b84829024da64e");
         variables.put(Constants.NAME, "Ahin");
         variables.put(Constants.CLASS, 12L);
         variables.put(Constants.AMOUNT, 1000L);
         execution.setVariables(variables);
         
         Payment mockPayment = new Payment("67cd2d31dd019866133eb08c", "67cd2c4fc1b84829024da64e", 12L, 1000L, true);
         when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);
         
         
        paymentProcess.execute(execution);
        Assertions.assertEquals(true, execution.getVariable(Constants.PAYMENT_SUCCESS));
    }
}
