package com.feeprocess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog {
    private long id;
    private String courseCode;       // Unique course identifier (e.g., CS101)
    private String courseName;       // Course Name (e.g., Data Structures)
    private String description;      // Course Description
    private String department;       // Department (e.g., Computer Science)
    private String academicProgram;  // Bachelor's, Master's, etc.
    private String subjects;   // List of subject codes linked to the course
    private int credits;             // Number of credits for the course
    private String facultyId;
    private double courseFee;
}
