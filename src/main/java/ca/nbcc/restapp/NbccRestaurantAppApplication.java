package ca.nbcc.restapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ca.nbcc.restapp.controller.EmployeeController;
import ca.nbcc.restapp.controller.LoginController;
import ca.nbcc.restapp.controller.ReservationController;
import ca.nbcc.restapp.controller.TestController;
import ca.nbcc.restapp.model.Department;
import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.model.EmployeeRole;
import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.model.Reservation;
import ca.nbcc.restapp.model.ReservationTimeGroup;
import ca.nbcc.restapp.model.ReservationTimes;
import ca.nbcc.restapp.repo.DepartmentJpaRepo;
import ca.nbcc.restapp.repo.DishJpaRepo;
import ca.nbcc.restapp.repo.EmployeeJpaRepo;
import ca.nbcc.restapp.repo.MenuJpaRepo;
import ca.nbcc.restapp.repo.ProductJpaRepo;
import ca.nbcc.restapp.repo.RTableJpaRepo;
import ca.nbcc.restapp.repo.ReservationJpaRepo;
import ca.nbcc.restapp.repo.ReservationTimeJpaRepo;
import ca.nbcc.restapp.service.RTableService;
import ca.nbcc.restapp.service.ReservationService;
import ca.nbcc.restapp.service.ReservationTimeService;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class NbccRestaurantAppApplication{
	
	private static DepartmentJpaRepo depRepo;
	private static DishJpaRepo dishRepo;
	private static EmployeeJpaRepo eRepo;
	private static MenuJpaRepo mRepo;
	private static ProductJpaRepo pRepo;
	private static ReservationTimeJpaRepo resTRepo;
	private static ReservationJpaRepo resRepo;
	private static RTableJpaRepo tableRepo;
	
	@Autowired
	public NbccRestaurantAppApplication(DepartmentJpaRepo depRepo, DishJpaRepo dishRepo,
			MenuJpaRepo mRepo, ProductJpaRepo pRepo, EmployeeJpaRepo eRepo, 
			ReservationTimeJpaRepo resTRepo, ReservationJpaRepo resRepo, RTableJpaRepo tableRepo){
		NbccRestaurantAppApplication.depRepo = depRepo;
		NbccRestaurantAppApplication.dishRepo = dishRepo;
		NbccRestaurantAppApplication.eRepo = eRepo;
		NbccRestaurantAppApplication.mRepo = mRepo;
		NbccRestaurantAppApplication.pRepo = pRepo;
		NbccRestaurantAppApplication.resTRepo = resTRepo;
		NbccRestaurantAppApplication.resRepo = resRepo;
		NbccRestaurantAppApplication.tableRepo = tableRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(NbccRestaurantAppApplication.class, args);
	
		/*********** ADDING DEFAULT VALUES TO THE DB ************/
		//TestController testController = new TestController(new DepartmentService(depRepo), 
		//		new DishService(dishRepo), new EmployeeService(eRepo), new MenuService(mRepo), new ProductService(pRepo))

		
		/*ReservationController resTestController = new ReservationController(new ReservationService(resRepo), new ReservationTimeService(resTRepo), null);
		
		TestController tC = new TestController(new RTableService(tableRepo));

		for(var t : tC.showTables()) {
			
			tC.deleteTable(t.getId());
		}
		
		tC.addTable(new RTable((long)52, 4, false, null));
		tC.addTable(new RTable((long)51, 4, false, null));
		tC.addTable(new RTable((long)50, 4, false, null));
		tC.addTable(new RTable((long)10, 4, false, null));
		tC.addTable(new RTable((long)11, 4, false, null));
		tC.addTable(new RTable((long)12, 4, false, null));
		tC.addTable(new RTable((long)40, 4, false, null));
		tC.addTable(new RTable((long)41, 6, false, null));
		tC.addTable(new RTable((long)42, 6, false, null));
		tC.addTable(new RTable((long)43, 6, false, null));
		tC.addTable(new RTable((long)22, 2, false, null));
		tC.addTable(new RTable((long)21, 2, false, null));
		tC.addTable(new RTable((long)20, 2, false, null));
		tC.addTable(new RTable((long)30, 8, false, null));

		System.out.println(tC.showTables());
		*/
	}
}
