package com.feeprocess.model;

public class Catalog {
    private String id;
    private double studentClass;
    private double fee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(double studentClass) {
        this.studentClass = studentClass;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
