package com.feeprocess.model;

public class Student {

    private String id;
    private String name;
    private long studentClass;
    private double amount;

    public Student() {}

    public Student(String id, String name, long studentClass, double amount) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.amount = amount;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getStudentClass() { return studentClass; }
    public void setStudentClass(long studentClass) { this.studentClass = studentClass; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
