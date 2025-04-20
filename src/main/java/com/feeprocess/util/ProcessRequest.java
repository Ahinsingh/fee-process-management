package com.feeprocess.util;

import lombok.Data;

@Data
public class ProcessRequest {
    private String courseCode;
    private String studentClass;
    private String studentId;
    private long payAmount;
}
