package com.feeprocess.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payment")
public class Payment {
    @Id
    private String id;
    private String studentId;
    private long studentClass;
    private double amount;
    private boolean paid;

    public Payment() {}

    public Payment(String id, String studentId, long studentClass, double dueAmount, boolean paid) {
        this.id = id;
        this.studentId = studentId;
        this.studentClass = studentClass;
        this.amount = dueAmount;
        this.paid = paid;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public long getStudentClass() { return studentClass; }
    public void setStudentClass(long studentClass) { this.studentClass = studentClass; }

    public double getDueAmount() { return amount; }
    public void setDueAmount(double dueAmount) { this.amount = dueAmount; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}
