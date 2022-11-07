package ca.nbcc.restapp.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
	ApplicationContext ctx;

	public CustomerController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	@GetMapping("/toCustomerView")
	public String toCustomerView() {
		return "index.html";
	}
}
