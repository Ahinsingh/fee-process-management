package com.feeprocess.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.feeprocess.model.Catalog;
import com.feeprocess.model.Student;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @GetMapping("/catalog/{id}")
    Catalog getCourse(@PathVariable("id") long id);

    @PostMapping("/catalog/update")
    Catalog updateStudent(Student student);
}