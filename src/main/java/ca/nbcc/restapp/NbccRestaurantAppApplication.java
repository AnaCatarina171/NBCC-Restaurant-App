package ca.nbcc.restapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.nbcc.restapp.controller.TestController;
import ca.nbcc.restapp.repo.DepartmentJpaRepo;
import ca.nbcc.restapp.repo.DishJpaRepo;
import ca.nbcc.restapp.repo.EmployeeJpaRepo;
import ca.nbcc.restapp.repo.MenuJpaRepo;
import ca.nbcc.restapp.repo.ProductJpaRepo;
import ca.nbcc.restapp.service.CustomerService;
import ca.nbcc.restapp.service.DepartmentService;
import ca.nbcc.restapp.service.DishService;
import ca.nbcc.restapp.service.EmployeeService;
import ca.nbcc.restapp.service.MenuService;
import ca.nbcc.restapp.service.ProductService;

@SpringBootApplication
public class NbccRestaurantAppApplication {
	
//	private static DepartmentJpaRepo depRepo;
//	private static DishJpaRepo dishRepo;
//	private static EmployeeJpaRepo eRepo;
//	private static MenuJpaRepo mRepo;
//	private static ProductJpaRepo pRepo;
//	
//	@Autowired
//	public NbccRestaurantAppApplication(DepartmentJpaRepo depRepo, DishJpaRepo dishRepo, EmployeeJpaRepo eRepo,
//			MenuJpaRepo mRepo, ProductJpaRepo pRepo){
//		NbccRestaurantAppApplication.depRepo = depRepo;
//		NbccRestaurantAppApplication.dishRepo = dishRepo;
//		NbccRestaurantAppApplication.eRepo = eRepo;
//		NbccRestaurantAppApplication.mRepo = mRepo;
//		NbccRestaurantAppApplication.pRepo = pRepo;
//	}

	public static void main(String[] args) {
		SpringApplication.run(NbccRestaurantAppApplication.class, args);
	
		/*********** ADDING DEFAULT VALUES TO THE DB ************/
		//TestController testController = new TestController(new DepartmentService(depRepo), 
		//		new DishService(dishRepo), new EmployeeService(eRepo), new MenuService(mRepo), new ProductService(pRepo))
	}
	
	
	
}
