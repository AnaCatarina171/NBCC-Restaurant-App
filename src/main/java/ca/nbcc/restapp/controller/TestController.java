package ca.nbcc.restapp.controller;

import java.util.List;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.service.CustomerService;
import ca.nbcc.restapp.service.DepartmentService;
import ca.nbcc.restapp.service.DishService;
import ca.nbcc.restapp.service.EmployeeService;
import ca.nbcc.restapp.service.FloorPlanService;
import ca.nbcc.restapp.service.MenuService;
import ca.nbcc.restapp.service.ProductService;
import ca.nbcc.restapp.service.RTableService;
import ca.nbcc.restapp.service.ReservationService;
import ca.nbcc.restapp.service.RestaurantService;
import ca.nbcc.restapp.service.SectionService;

public class TestController {
	//CustomerService cs;
	//DepartmentService deps;
	//DishService dishs;
	//EmployeeService es;
	//FloorPlanService fps;
	//MenuService ms;
	//ProductService ps;
	//ReservationService reservations;
	//RestaurantService  restaurantts;
	//SectionService ss;
	RTableService tS;
	
	public TestController(/* DepartmentService deps, DishService dishs, EmployeeService es,
			MenuService ms, ProductService ps,*/ RTableService tS) {
		super();
		/*this.deps = deps;
		this.dishs = dishs;
		this.es = es;
		this.ms = ms;
		this.ps = ps;*/
		this.tS = tS;
	}
	
	/*public void addTable(RTable r) {
		
		tS.addNewRTable(r);
	}*/
	
	public List<RTable> showTables() {
		
		return tS.getAllRTable();
	}
	
	public void deleteTable(Long t) {
		
		tS.deleteRTable(t);
	}
}
