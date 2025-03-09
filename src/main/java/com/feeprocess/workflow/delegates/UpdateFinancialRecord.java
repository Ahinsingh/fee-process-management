package com.feeprocess.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feeprocess.util.Constants;
import com.feeprocess.util.WorkflowLogger;


@Service("UpdateFinancialRecord")
public class UpdateFinancialRecord implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) {

        WorkflowLogger.info(logger, "Financial Record is completed", "Financial Record is completed");
        execution.setVariable(Constants.FINANCIAL_RECORD_UPDATED, true);
    }
}