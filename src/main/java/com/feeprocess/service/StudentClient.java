package com.feeprocess.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.feeprocess.model.Student;

@FeignClient(name = "student-service")
public interface StudentClient {

    @GetMapping("/students/{id}")
    Student getStudentById(@PathVariable("id") long id);

    @PostMapping("/students/update")
	Student updateStudent(Student student);
}