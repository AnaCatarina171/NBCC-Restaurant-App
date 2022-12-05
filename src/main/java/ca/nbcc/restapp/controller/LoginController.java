package ca.nbcc.restapp.controller;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.model.EmployeeDetails;
import ca.nbcc.restapp.model.EmployeeRole;
import ca.nbcc.restapp.service.EmployeeService;

@Controller
public class LoginController {

	@Autowired
	private EmployeeService eS;

	private ApplicationContext ctx;

	@Autowired
	public LoginController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}

	public LoginController(EmployeeService eS) {
		super();
		this.eS = eS;
	}

	@GetMapping("/")
	public String toIndex(Model model) {

		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication(); if (authentication ==
		 * null || authentication instanceof AnonymousAuthenticationToken) {
		 * 
		 * model.addAttribute("empDet", new Employee());
		 * 
		 * return "login2"; }
		 */
		return "index";
	}
	
	@GetMapping("/goToLogin")
	public String toLoginIn(Model model) {
		
		  Authentication authentication =
		  SecurityContextHolder.getContext().getAuthentication(); if (authentication ==
		  null || authentication instanceof AnonymousAuthenticationToken) {
		  
		  model.addAttribute("empDet", new Employee());
		  
		  return "login2"; }
		 
		return "redirect:/userPanel";
	}

	// click a link to LOG IN
	@RequestMapping(value = "/processLogin", method = { RequestMethod.POST })
	public String showLogInPage(Model model, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		for (var currentEmp : eS.getAllEmployees()) {

			if (currentEmp.getUsername().equals(username)) {

				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				if (passwordEncoder.matches(password, currentEmp.getPassword())) {

					// Successful login
					request.getSession().setAttribute("CURRENT_USER", currentEmp.getUsername());

					model.addAttribute("emp", currentEmp);
					return "redirect:/userPanel";
				}
			}
			error = "1";
		}

		if (error != null && logout == null) {
			model.addAttribute("error", "Invalid Credentials provided.");
		}
		if (logout != null) {
			model.addAttribute("message", "Logged out successfully.");
		}

		return "login2";
	}

	@RequestMapping(value = "/register")
	public String showRegisterPage(Model model, @Valid Employee emp, BindingResult br, HttpSession session) {
		model.addAttribute("emp", new Employee());
		return "register-user";
	}

	@RequestMapping(value = "/home")
	public String showHome(Model model, @Valid Employee emp) {

		Employee currentUser = eS.findEmployeeByUsername(emp.getUsername().trim());
		// model.addAttribute("emp", currentUser);
		int i = 1;
		for (Employee u : eS.getAllEmployees()) {
			System.out.println(i + " - " + u);
			i++;
		}
		return "home";
	}

	@RequestMapping(value = "/processRegister", method = RequestMethod.POST)
	public String processRegister(HttpSession session, Model model, @Valid Employee emp, BindingResult br) {

		if (br != null) {
			if (br.hasErrors()) {
				return "login";
			}
		}
		// SECURITY STEP #1
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Employee foundUser = eS.findEmployeeByUsername(emp.getUsername().trim());

		if (foundUser != null) {
			model.addAttribute("errorMsg", "Username already exists");
			return "register-user";
		}

		else {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator v = factory.getValidator();

			Set<ConstraintViolation<Employee>> violations = v.validate(emp);

			for (ConstraintViolation<Employee> cv : violations) {
				System.out.println("" + emp + ": ");
				String field = null;
				for (Node node : cv.getPropertyPath()) {
					field = node.getName();
				}
				System.out.println("- PROBLEM: " + cv.getMessage());
				System.out.println("- FIELD: " + field);
			}

			if (violations.size() == 0) {

				emp.setPassword(encoder.encode(emp.getPassword().trim()));
				eS.addNewEmployee(emp);

				return "redirect:/";
			}

			model.addAttribute("errorMsg", "Invalid Information");
			return "register-user";
		}
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "password", required = false) String password, Principal principal) {
		Employee currentUser = eS.findEmployeeByUsername(principal.getName().trim());

		if (firstName != null && !firstName.trim().equals("")) {
			// currentUser.setImgURI(firstName);
		}
		if (lastName != null && !lastName.trim().equals("")) {
			// currentUser.setImgURI(firstName);
		}
		if (password != null && !password.trim().equals("")) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			currentUser.setPassword(encoder.encode(password));
		}
		eS.updateEmployee(currentUser);
		return "redirect:/home";

	}
	
	@GetMapping("/goToLogout")
	public String logout(HttpSession httpSession, Model model) {
		
		httpSession.invalidate();
		
		model.addAttribute("logout", "Logged out successfully.");
		
		return "login2";
	}
}
