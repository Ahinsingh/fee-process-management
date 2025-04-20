package com.feeprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.feeprocess.model.FinancicalRecord;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancicalRecord, Long> {

	@Query("SELECT f FROM FinancicalRecord f WHERE f.studentId = :studentId AND f.courseCode = :courseCode")
	FinancicalRecord findByStudentIdAndCourseCode(@Param("studentId") long studentId, @Param("courseCode") String courseCode);

}
