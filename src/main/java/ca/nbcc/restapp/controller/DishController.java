package ca.nbcc.restapp.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.nbcc.restapp.service.DishService;
import ca.nbcc.restapp.service.MenuService;

@Controller
public class DishController {
	
	private ApplicationContext ctx;
	private MenuService ms;
	private DishService ds;
	
	public DishController(ApplicationContext ctx, MenuService ms, DishService ds) {
		super();
		this.ctx = ctx;
		this.ms = ms;
		this.ds = ds;
	}
	
	
}
