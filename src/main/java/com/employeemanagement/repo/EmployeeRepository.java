package com.employeemanagement.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeemanagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrJobTitleContainingIgnoreCase(
			String firstName, String lastName, String department,
			String position);

	@Query("SELECT e FROM Employee e WHERE "
			+ "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrJobTitleContainingIgnoreCase(
			@Param("keyword") String keyword, Pageable pageable);
}
