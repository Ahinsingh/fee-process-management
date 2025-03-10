package com.feeprocess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;

@Service
public class FeeProcessService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Payment createPayment(Payment payment) {

		String studentUrl = "http://localhost:8082/student-service/student/" + payment.getStudentId();
		ResponseEntity<Student> response = restTemplate.getForEntity(studentUrl, Student.class);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			Student student = response.getBody();

			if (!student.getId().equals(payment.getStudentId())) {
				return null;
			} else {
				Payment savedPayment = paymentRepository.save(payment);
				return savedPayment;
			}

		} else {
			System.out.println("Student not found: " + payment.getStudentId());
		}
		return null;
	}
	
	
	public Student student(String studentId) {
		String studentUrl = "http://localhost:8082/student-service/students/" + studentId;
		ResponseEntity<Student> response = restTemplate.getForEntity(studentUrl, Student.class);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			Student student = response.getBody();
			if (student!=null) {
				return student;
			} 
		}
		return null;
	}
	
	
	public Student resetStudentFee(Student student) {
	    String studentUrl = "http://localhost:8082/student-service/students/update";
	    ResponseEntity<Student> response = restTemplate.postForEntity(studentUrl, student, Student.class);
	    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	        return response.getBody();
	    }
	    return null;
	}
	
}
