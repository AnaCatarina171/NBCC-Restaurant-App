package ca.nbcc.restapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	ApplicationContext ctx;
	
	
	@Autowired
	public IndexController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}

	@GetMapping("/") 
	public String toIndex() {
		return "index";
	}
}
