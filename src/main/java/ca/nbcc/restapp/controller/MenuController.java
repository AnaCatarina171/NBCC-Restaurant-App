package ca.nbcc.restapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.restapp.model.Dish;
import ca.nbcc.restapp.model.DishCategory;
import ca.nbcc.restapp.model.DishNationality;
import ca.nbcc.restapp.model.Menu;
import ca.nbcc.restapp.service.DishService;
import ca.nbcc.restapp.service.MenuService;

@Controller
public class MenuController {
	
	private ApplicationContext ctx;
	private MenuService ms;
	private DishService ds;
	
	
		
	public MenuController(ApplicationContext ctx, MenuService ms, DishService ds) {
		super();
		this.ctx = ctx;
		this.ms = ms;
		this.ds = ds;
	}
	
	/*
	 * TO DO: 
	 * Show a list to the user what menus are going to be displayed 
	 */
	
	
	@GetMapping("/customerMenuDisplay")
	public String userMenuPage(Model model) {
		List<Menu> menusToDisplay = new ArrayList<>();
		
		menusToDisplay = ms.getMenusToDisplay();
		System.out.println(menusToDisplay.toString());
		model.addAttribute("menusList", menusToDisplay);
		return "display-userMenu";
	}
	
	@GetMapping("/toMenuPanel")
	public String toMenuPanel(Model model) {		
		return "user-menuPanel";
	}
	
	@GetMapping("/toAddMenu")
	public String toCreateMenu(Model model) {		
		Menu menuToAdd = new Menu();	
		ms.saveMenu(menuToAdd);
		
		setupPopulateMenuPage(model, 0);		
		return "user-addMenuDetails";
	}
	
	@PostMapping("/processMenuDetails")
	public String processMenuDetails(Model model, Menu menuToAdd) {
		menuToAdd.setDate(LocalDate.now());
		ms.saveMenu(menuToAdd);
		
		setupPopulateMenuPage(model, 0);
		return"redirect:/toPopulateMenu";
	}
	
	@GetMapping("/toPopulateMenu")
	public String toPopulateMenu(Model model) {
		
		setupPopulateMenuPage(model, 0);
		return "user-populateMenu";
	}
	
	@GetMapping("/toPopulateMenu/{pageNum}")
	public String toPopulateMenu(Model model, @PathVariable("pageNum") int pageNum) {		
		setupPopulateMenuPage(model, pageNum);
		return "user-populateMenu";
	}
	
	@PostMapping("/toPopulateMenu")
	public String toPopulateMenu(Model model, @RequestParam("searchTerm") String searchTerm) {
		System.out.println(searchTerm.toString());
		setupPopulateMenuPage(model, searchTerm);
		return "user-populateMenu";
	}
	
	@PostMapping("/processAddNewItem")
	public String processAddNewItem(Model model, Dish dishToAdd) {
		Menu menuToPopulate = ms.getLastMenu();
		dishToAdd.setMenu(menuToPopulate);
		ds.addNewDish(dishToAdd);
		ms.saveMenu(menuToPopulate);

		setupPopulateMenuPage(model, 0);
		return "user-populateMenu";
	}
	
	@GetMapping("/addDishToMenu/{dId}")
	public String addDishToMenu(Model model, @PathVariable("dId") long rId) {
		Menu menuToPopulate = ms.getLastMenu();
		List<Dish> menuDishList = menuToPopulate.getDishList();
		menuDishList.add(ds.getDishById(rId));
		menuToPopulate.setDishList(menuDishList);
		ms.saveMenu(menuToPopulate);
		
		setupPopulateMenuPage(model, 0);
		return "user-populateMenu";
	}
	
	
	private List<Pageable> generatePageableDishes(int totalItems, int itemPerPage){
		List<Pageable> pageableDishes = new ArrayList<>();
		int totalPages = 0;
		
		if(totalItems/itemPerPage > (int)(totalItems / itemPerPage) ) {
			totalPages = (int) totalItems / itemPerPage + 1;
		}else {
			totalPages = (int) totalItems / itemPerPage;
		}
		
		
		for (int i = 0; i < totalPages ; i++) {
			pageableDishes.add(PageRequest.of(itemPerPage, i));
		}
		System.out.println(pageableDishes.toString());
		return pageableDishes;
	}
	
	private void setupPopulateMenuPage(Model model, int pageNumber) {
		Dish dishToAdd= new Dish();
		Menu menuToPopulate = ms.getLastMenu();
		EnumSet<DishCategory> categories = EnumSet.allOf(DishCategory.class);
		EnumSet<DishNationality> nationalities = EnumSet.allOf(DishNationality.class);
		List<Dish> allDishes = ds.getAllDishes();
		int itemPerPage = 10;
		List<Pageable> pageableDishes = generatePageableDishes(allDishes.size(), itemPerPage);
		System.out.println(pageableDishes.toString());
		if(pageableDishes.size() != 0) {
			List<Dish> dishList = ds.getAllDishesPaginated(pageableDishes.get(pageNumber));
			model.addAttribute("dishList", dishList);
		}else {
			model.addAttribute("dishList", ds.getAllDishes());
		}
		
		
		model.addAttribute("menuToPopulate", menuToPopulate);
		model.addAttribute("dishToAdd", dishToAdd);		
		model.addAttribute("categories", categories);
		model.addAttribute("nationalities", nationalities);
	}
	
	private void setupPopulateMenuPage(Model model, String searchTerm) {
		Dish dishToAdd= new Dish();
		List<Dish> dishList;
		if(searchTerm == null || searchTerm.length() == 0) {
			dishList = ds.getAllDishes();
		}else {
			dishList = ds.searchDishByName(searchTerm);
		}		
		Menu menuToPopulate = ms.getLastMenu();
		EnumSet<DishCategory> categories = EnumSet.allOf(DishCategory.class);
		EnumSet<DishNationality> nationalities = EnumSet.allOf(DishNationality.class);
		
		model.addAttribute("menuToPopulate", menuToPopulate);
		model.addAttribute("dishToAdd", dishToAdd);
		model.addAttribute("dishList", dishList);
		model.addAttribute("categories", categories);
		model.addAttribute("nationalities", nationalities);
	}
	
}
