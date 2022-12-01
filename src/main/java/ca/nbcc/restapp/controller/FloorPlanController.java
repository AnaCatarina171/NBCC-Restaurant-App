package ca.nbcc.restapp.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FloorPlanController {

ApplicationContext ctx;
	
	public FloorPlanController(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	@GetMapping("/toFloorPlanAdmin")
	public String goToFloorPlan() {
		
		return "floor-plan";
	}
}
