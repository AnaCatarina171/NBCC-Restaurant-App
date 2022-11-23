package ca.nbcc.restapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.service.EmployeeService;

public class EmployeeController {

	@Autowired
	private EmployeeService eS;
	
	@Autowired
	public EmployeeController(EmployeeService eS) {
		super();
		this.eS=eS;
	}
	
	@PostMapping("addEmployee")
	public void addEmployeeTest(Employee e) {
		eS.addNewEmployee(e);
		
	}
	
	@PostMapping("getAllEmployees")
	public List<Employee> getEmployees(){
		return eS.getAllEmployees();
	}
}
