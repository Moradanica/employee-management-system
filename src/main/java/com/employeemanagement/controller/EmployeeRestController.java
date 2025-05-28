package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.model.Employee;
import com.employeemanagement.service.EmployeeService;

@RestController
@RequestMapping("/rest")
public class EmployeeRestController {

	@Autowired
	EmployeeService empsService;

	@GetMapping("/employees")
	public List<Employee> employeeList() {
		List<Employee> empList = empsService.getAllEmployees();
		return empList;
	}

	@GetMapping("/search")
	public List<Employee> search(@RequestParam String keyword) {
		List<Employee> empList = empsService.searchEmployee(keyword);
		return empList;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("employee/edit/{id}")
	public Employee getEmp(@PathVariable int id) {
		System.out.println(
				"getById rest controller called! " + empsService.getById(id));
		return empsService.getById(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/employee/delete/{id}")
	public void deleteEmp(@PathVariable int id) {
		empsService.delete(id);
	}

	@PreAuthorize("hasRole('USER') AND hasRole('ADMIN')")
	@PutMapping("/employee/update")
	public ResponseEntity<Employee> update(@ModelAttribute Employee emp) {
		Employee saved = empsService.save(emp);
		return ResponseEntity.ok(saved);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/employee/add")
	public ResponseEntity<Employee> add(@ModelAttribute Employee emp) {
		Employee added = empsService.save(emp);
		return ResponseEntity.ok(added);
	}

}
