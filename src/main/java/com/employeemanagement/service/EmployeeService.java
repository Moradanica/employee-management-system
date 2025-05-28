package com.employeemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.employeemanagement.model.Employee;
import com.employeemanagement.repo.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository empRepo;

	public List<Employee> getAllEmployees() {
		List<Employee> emps = empRepo.findAll();
		return emps;
	}

	public Page<Employee> getPaginatedEmployees(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return empRepo.findAll(pageable);
	}

	public Page<Employee> searchEmployees(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return empRepo
				.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrJobTitleContainingIgnoreCase(
						keyword, pageable);
	}

	public List<Employee> searchEmployee(String keyword) {
		List<Employee> result = empRepo
				.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrJobTitleContainingIgnoreCase(
						keyword, keyword, keyword, keyword);
		return result;
	}

	public Employee getById(int id) {
		return empRepo.findById(id).orElseThrow();
	}

	public Employee save(Employee emp) {
		return empRepo.save(emp);
	}

	public void delete(int id) {
		empRepo.deleteById(id);
	}

}
