package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.model.EmployeeDetails;
import ca.nbcc.restapp.repo.EmployeeJpaRepo;

@Service
public class EmployeeService {

	private EmployeeJpaRepo er;
	
	@Autowired
	public EmployeeService(EmployeeJpaRepo er) {
		super();
		this.er = er;
	}
	
	public Employee findEmployeeByUsername(String username) {
		
		return er.findByusername(username);
	}
	
	public Employee findEmployeeById(Long eMID_LONG) throws Exception{
		if(er.findById((long)eMID_LONG).isPresent()) {
			return er.findById((long)eMID_LONG).get();
		}
		else if(er.findById((long)eMID_LONG).isEmpty()) {
			throw new Exception("Employee not found: ID " + eMID_LONG);
		}
		return null;
	}
	
	public Employee findEmployeeDetailsById(Long eMID_LONG) throws Exception{
		if(er.findById((long)eMID_LONG).isPresent()) {
			Employee emp = er.findById((long)eMID_LONG).get();
			
			return new EmployeeDetails(emp);
		}
		else if(er.findById((long)eMID_LONG).isEmpty()) {
			throw new Exception("Employee not found: ID " + eMID_LONG);
		}
		return null;
	}
	
	public List<Employee> getAllEmployees(){
		return er.findAll();
	}
	
	public Employee addNewEmployee(Employee e) {
		return er.save(e);
	}

	public Employee updateEmployee(Employee e) {
		return er.save(e);
	}

	public void deleteEmployee(Long eMID_LONG) {
			
		er.deleteById(eMID_LONG);
	}
}
