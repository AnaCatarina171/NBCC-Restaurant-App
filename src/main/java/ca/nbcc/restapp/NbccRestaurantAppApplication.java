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
import ca.nbcc.restapp.controller.TestController;
import ca.nbcc.restapp.model.Department;
import ca.nbcc.restapp.model.Employee;
import ca.nbcc.restapp.model.EmployeeRole;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class NbccRestaurantAppApplication{
	
	private static DepartmentJpaRepo depRepo;
	private static DishJpaRepo dishRepo;
	private static EmployeeJpaRepo eRepo;
	private static MenuJpaRepo mRepo;
	private static ProductJpaRepo pRepo;
	
	@Autowired
	public NbccRestaurantAppApplication(DepartmentJpaRepo depRepo, DishJpaRepo dishRepo,
			MenuJpaRepo mRepo, ProductJpaRepo pRepo, EmployeeJpaRepo eRepo){
		NbccRestaurantAppApplication.depRepo = depRepo;
		NbccRestaurantAppApplication.dishRepo = dishRepo;
		NbccRestaurantAppApplication.eRepo = eRepo;
		NbccRestaurantAppApplication.mRepo = mRepo;
		NbccRestaurantAppApplication.pRepo = pRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(NbccRestaurantAppApplication.class, args);
	
		/*********** ADDING DEFAULT VALUES TO THE DB ************/
		//TestController testController = new TestController(new DepartmentService(depRepo), 
		//		new DishService(dishRepo), new EmployeeService(eRepo), new MenuService(mRepo), new ProductService(pRepo))

	}
}
