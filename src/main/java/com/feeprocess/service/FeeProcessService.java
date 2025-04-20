package com.feeprocess.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feeprocess.model.Catalog;
import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;

@Service
public class FeeProcessService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private StudentClient apiClient;

	@Autowired
	private CatalogClient catalogClient;

	public Payment createPayment(Payment payment) {

		Student student = apiClient.getStudentById(payment.getStudentId());
		if (student.getId() != payment.getStudentId()) {
			return null;
		} else {
			Payment savedPayment = paymentRepository.save(payment);
			return savedPayment;
		}
	}

	public List<Payment> getPaymentsToday() {
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = today.atStartOfDay();
		LocalDateTime endOfDay = today.atTime(23, 59, 59);
		return paymentRepository.findByCreatedAtBetween(startOfDay, endOfDay);
	}

	public List<Payment> getPaymentsYesterday() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDateTime startOfDay = yesterday.atStartOfDay();
		LocalDateTime endOfDay = yesterday.atTime(23, 59, 59);
		return paymentRepository.findByCreatedAtBetween(startOfDay, endOfDay);
	}

	public List<Payment> getPaymentsLastYear() {
		int lastYear = LocalDate.now().getYear() - 1;
		LocalDateTime startOfYear = LocalDate.of(lastYear, 1, 1).atStartOfDay();
		LocalDateTime endOfYear = LocalDate.of(lastYear, 12, 31).atTime(23, 59, 59);
		return paymentRepository.findPaymentsByYear(startOfYear, endOfYear);
	}

	public Student student(long studentId) {
		Student student = apiClient.getStudentById(studentId);
		if (student == null || student.getId() != studentId) {
			throw new IllegalArgumentException("Student verification failed.");
		} else {
			return student;
		}
	}

	public Catalog getCatalog(long courseId) {
		Catalog catalog = catalogClient.getCourse(courseId);
		if (catalog == null || catalog.getId()!=courseId) {
			throw new IllegalArgumentException("Student verification failed.");
		} else {
			return catalog;
		}

	}

	public Student resetStudentFee(Student student) {
		Student studentRes = apiClient.updateStudent(student);
		if (studentRes != null) {
			return studentRes;
		}
		return null;
	}

	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	public Optional<Payment> findById(long id) {
		return paymentRepository.findById(id);
	}

}
