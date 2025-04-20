package com.feeprocess.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financical_record")
public class FinancicalRecord {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long studentId;
    private String studentClass;
    private String courseCode;
    private double paidAmount;
    private double pendingAmount;
    private String status;
    private String accadamicYear;
    @CreatedDate
    private LocalDateTime createdAt;
	    
}
