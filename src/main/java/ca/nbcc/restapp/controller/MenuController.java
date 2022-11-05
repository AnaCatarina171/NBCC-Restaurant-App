package ca.nbcc.restapp.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
	
	private ApplicationContext ctx;

	public MenuController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	
	@GetMapping("/customerMenuDisplay")
	public String toCustomerMenuDisplay() {
		
	}
}
