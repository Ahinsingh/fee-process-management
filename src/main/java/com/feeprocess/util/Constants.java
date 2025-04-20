package com.feeprocess.util;

/**
 * @implSpec Constants class used to maintain constants used in project
 */
public class Constants {

    public static final String STUDENT_AVAILABLE = "StudentAvailable";
    public static final String STUDENT_HAS_DUE = "CheckDueAmount";
    public static final String RECEIPT_GENERATED = "IsReciptGenerating";
    public static final String PAYMENT_DONE = "Payment Done";
    public static final String PAYMENT_SUCCESS = "paymentSuccess";
    public static final String FINANCIAL_RECORD_UPDATED = "financial record is updated";
    public static final String ID = "studentId";
    public static final String NAME = "name";
    public static final String COURSECODE = "courseCode";
    public static final String STUDENTCLASS = "className";
    
    public static final String AMOUNT = "payAmount";
    public static final String MAIL_SENT = "Mail Sent";

    private Constants() {
        throw new IllegalStateException("Constants Utility Class. Cannot be instantiated.");
    }

}
