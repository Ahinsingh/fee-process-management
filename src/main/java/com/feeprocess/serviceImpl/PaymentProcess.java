package com.feeprocess.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.feeprocess.dto.PaymentDTO;
import com.feeprocess.model.Catalog;
import com.feeprocess.model.Payment;
import com.feeprocess.model.Student;
import com.feeprocess.repository.PaymentRepository;
import com.feeprocess.service.FeeProcessService;
import com.feeprocess.util.WorkflowLogger;

@Service
public class PaymentProcess {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private FeeProcessService service;

	@Autowired
	UpdateFinancialRecord updateFinRec;

	@Autowired
	GenerateReceipt generateReceipt;

	public static final String CHECK_PAYMENT_DONE = "Check Payment Done";
	public static final String CHECK_DUE = "Verify Due Amount";
	public static final String VERIFY_STUDENT = "Verify Student";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Payment executePayment(PaymentDTO paymentDto) {
		WorkflowLogger.info(logger, CHECK_PAYMENT_DONE, "Check Payment Done");
		boolean paymentSuccess = false;
		Payment savedPayment = null;
		long studentId = paymentDto.getStudentId();
		String studentClass = paymentDto.getStudentClass();
		String courseCode = paymentDto.getCourseCode();
		long courseId = paymentDto.getCourseId();
		double newAmount = paymentDto.getAmount();
		
		
		Optional<Payment> lastPayment = paymentRepository
			    .findTopByStudentIdOrderByCreatedAtDesc(studentId, PageRequest.of(0, 1))
			    .stream()
			    .findFirst();

		
//		Optional<Payment> lastPayment = paymentRepository.findTopByStudentIdOrderByCreatedAtDesc(studentId);
		double prePaidAmt = 0;
		if (studentId != 0 && courseId!=0 && newAmount != 0
				&& (lastPayment.isEmpty() || !lastPayment.get().isFullyPaid())) {
			Student student = service.student(studentId);
			if (student.getPendingFee() >= newAmount) {
				Optional<List<Payment>> previousPayments = paymentRepository.findByStudentIdAndCourseCode(studentId,
						courseCode);
				double totalPaid = 0;
				prePaidAmt = previousPayments.isPresent()
						? previousPayments.get().stream().mapToDouble(Payment::getPaidAmount).sum()
						: 0;
				Payment payment = new Payment();
				payment.setCourseId(courseId);
				payment.setCourseCode(courseCode);
				payment.setStudentClass(studentClass);
				payment.setStudentId(studentId);
				payment.setPaidAmount(newAmount);
				payment.setCreatedAt(LocalDateTime.now());
				totalPaid = newAmount + prePaidAmt;
				double pendingAmount = 0;

				Catalog catalog = service.getCatalog(courseId);

				if (catalog != null) {
					pendingAmount = catalog.getCourseFee() - totalPaid;
					if (totalPaid >= catalog.getCourseFee()) {
						payment.setStatus("PAID");
					} else {
						payment.setStatus("PENDING");
					}
					System.out.println(
							"Catalog Fee for class " + student.getStudentClass() + " : " + catalog.getCourseFee());
				} else {
					System.out.println("Catalog not found for class: " + student.getStudentClass());
				}

				payment.setPendingAmount(pendingAmount);

				if (pendingAmount <= 0) {
					payment.setFullyPaid(true);
					student.setStatus(true);
				} else {
					payment.setFullyPaid(false);
					student.setStatus(false);
				}

				savedPayment = paymentRepository.save(payment);
//        		  execution.setVariable("paymentId", savedPayment.getId());
				if (savedPayment != null) {
					student.setPendingFee(pendingAmount);
					Student responseObj = service.resetStudentFee(student);
					updateFinRec.updateRec(savedPayment.getId(), studentId, courseCode);
					paymentSuccess = true;
					generateReceipt.generateReceipt(studentId, studentClass);
				}
				WorkflowLogger.info(logger, "Verify_Payment", "Payment Success");
			} else {
				paymentSuccess = false;
				WorkflowLogger.error(logger, "Verify_Payment", "Payment Failed");
			}
		} else {
			paymentSuccess = false;
			WorkflowLogger.error(logger, "Verify_Payment", "Payment Failed");
		}
		return savedPayment;
//        execution.setVariable(Constants.PAYMENT_SUCCESS, paymentSuccess);
	}

	public boolean checkDueAmount(long studentId, double payAmount) {
		WorkflowLogger.info(logger, CHECK_DUE, "Check the amount");
		boolean studentDueAmount;
		Student student = service.student(studentId);
		if (studentId != 0 && student != null && payAmount > 0 && student.getPendingFee() > 0) {
			studentDueAmount = true;
			WorkflowLogger.info(logger, CHECK_DUE, "we can processing the fee");
		} else {
			studentDueAmount = false;
			WorkflowLogger.error(logger, CHECK_DUE, "we can't processing the fee.Please check the Admin");
		}
		return studentDueAmount;
	}

	public boolean verifyStudent(Long studentId, String studentClass) {
		WorkflowLogger.info(logger, VERIFY_STUDENT, "Check its a valid student");
		boolean studentAvailable;
		Student student = service.student(studentId);
		if (studentId != 0 && studentClass != "" && student != null) {
			studentAvailable = true;
			WorkflowLogger.info(logger, VERIFY_STUDENT, "we can processing the fee");
		} else {
			studentAvailable = false;
			WorkflowLogger.error(logger, VERIFY_STUDENT,
					"we cannot process payement. Student Id and Name is not available.");
		}

		return studentAvailable;
	}
}
