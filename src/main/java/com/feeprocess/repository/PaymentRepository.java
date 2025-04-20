package com.feeprocess.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feeprocess.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p WHERE p.studentId = :studentId")
	List<Payment> findByStudentId(@Param("studentId") long studentId);

	Optional<Payment> findById(Long id);

	@Query("SELECT p FROM Payment p WHERE p.studentId = :studentId ORDER BY p.createdAt DESC")
	List<Payment> findTopByStudentIdOrderByCreatedAtDesc(@Param("studentId") long studentId, Pageable pageable);


	@Query("SELECT p FROM Payment p WHERE p.studentId = :studentId AND p.courseCode = :courseCode")
	Optional<List<Payment>> findByStudentIdAndCourseCode(@Param("studentId") long studentId,
			@Param("courseCode") String courseCode);

	@Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :start AND :end")
	List<Payment> findByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	// Fetch payments for a specific year
	@Query("SELECT p FROM Payment p WHERE p.createdAt >= :startOfYear AND p.createdAt < :endOfYear")
	List<Payment> findPaymentsByYear(@Param("startOfYear") LocalDateTime startOfYear,
			@Param("endOfYear") LocalDateTime endOfYear);
}
