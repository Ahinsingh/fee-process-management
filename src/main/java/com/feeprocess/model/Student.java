package com.feeprocess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	private long id;
    private String name;
    private String grade;
    private String mobileNumber;
    private String schoolName;
    private String courseCode;
    private long courseId;
    private String studentClass;
    private double pendingFee;
    private boolean status;
}
