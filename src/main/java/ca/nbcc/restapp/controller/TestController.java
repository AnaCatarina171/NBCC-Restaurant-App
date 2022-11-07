package ca.nbcc.restapp.controller;

import ca.nbcc.restapp.service.CustomerService;
import ca.nbcc.restapp.service.DepartmentService;
import ca.nbcc.restapp.service.DishService;
import ca.nbcc.restapp.service.EmployeeService;
import ca.nbcc.restapp.service.FloorPlanService;
import ca.nbcc.restapp.service.MenuService;
import ca.nbcc.restapp.service.ProductService;
import ca.nbcc.restapp.service.ReservationService;
import ca.nbcc.restapp.service.RestaurantService;
import ca.nbcc.restapp.service.SectionService;

public class TestController {
	//CustomerService cs;
	DepartmentService deps;
	DishService dishs;
	EmployeeService es;
	//FloorPlanService fps;
	MenuService ms;
	ProductService ps;
	//ReservationService reservations;
	//RestaurantService  restaurantts;
	//SectionService ss;
	
	public TestController( DepartmentService deps, DishService dishs, EmployeeService es,
			MenuService ms, ProductService ps) {
		super();
		this.deps = deps;
		this.dishs = dishs;
		this.es = es;
		this.ms = ms;
		this.ps = ps;
	}
	
	
}
