package com.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employeemanagement.model.Employee;
import com.employeemanagement.service.EmployeeService;

@Controller
// @RequestMapping("/")
public class EmployeeController {

	@Autowired
	EmployeeService empsService;

	@GetMapping({"/home"})
	public String getEmployees(Model model,
			@RequestParam(defaultValue = "0") int page) {
		// List<Employee> empList = empsService.getAllEmployees();

		int pageSize = 20;

		Page<Employee> empList = empsService.getPaginatedEmployees(page,
				pageSize);

		if (empList.getTotalPages() == 0) {
			empList = null;
		}

		System.out.println("/home: " + empList.getTotalPages());
		model.addAttribute("empList", empList);
		return "home";

	}

	// MY METHOD
	// @GetMapping("/search")
	// public String search(@RequestParam String keyword,
	// @RequestParam(defaultValue = "0") int page, Model model) {
	//
	// int pageSize = 20;
	// // List<Employee> searchEmployee = null;
	// Page<Employee> searchEmployee;
	//
	// if (keyword == null || keyword.isBlank()) {
	// searchEmployee = empsService.getPaginatedEmployees(page, pageSize);
	// } else {
	// searchEmployee = empsService.searchEmployees(keyword, page,
	// pageSize);
	// }
	//
	// model.addAttribute("empList", searchEmployee);
	// return "/home :: employeeTableBody";
	// // return "home";
	// }

	@GetMapping("/search")
	public String search(@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page, Model model) {

		int pageSize = 20;
		Page<Employee> empList = keyword == null || keyword.isBlank()
				? empsService.getPaginatedEmployees(page, pageSize)
				: empsService.searchEmployees(keyword, page, pageSize);

		System.out.println("/search Total Pages: " + empList.getTotalPages());
		System.out.println(
				"/search total elements " + empList.getTotalElements());
		System.out.println("/search current page : " + page);
		
		if(empList.getTotalElements() == 0) {
			model.addAttribute("empList", null);
		} else {
			model.addAttribute("empList", empList);
		}

		
		model.addAttribute("keyword", keyword);

		if (empList.getTotalPages() > 0) {

		}
		// return "/home :: resultBody";
		// return "fragments/employee-table :: resultBody";
		return "home";
	}

}
