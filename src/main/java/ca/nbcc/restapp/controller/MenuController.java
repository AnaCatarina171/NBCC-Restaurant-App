package ca.nbcc.restapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
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
import ca.nbcc.restapp.service.PaginationService;

@Controller
public class MenuController {
	
	private ApplicationContext ctx;
	private MenuService ms;
	private DishService ds;
	private PaginationService<Dish> pags;
	
	
		
	public MenuController(ApplicationContext ctx, MenuService ms, DishService ds, PaginationService<Dish> pags) {
		super();
		this.ctx = ctx;
		this.ms = ms;
		this.ds = ds;
		this.pags = pags;
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
		List<Menu> allMenus = ms.getAllMenus();
		model.addAttribute("allMenus", allMenus);
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
	public String processMenuDetails(Model model, Menu menuToPopulate) {
		Menu menuToAdd = ms.getLastMenu();
		System.out.println(menuToPopulate);
		System.out.println(menuToAdd);
		menuToAdd.setTitle(menuToPopulate.getTitle());
		menuToAdd.setDescription(menuToPopulate.getDescription());
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
	
	@GetMapping("/toSaveMenu")
	public String toSaveMenu(Model model) {
		Menu menuToSave = ms.getLastMenu();
		System.out.println(menuToSave.toString());
		menuToSave.setDate(LocalDate.now());
		ms.saveMenu(menuToSave);
		
		return "redirect:/toMenuPanel";
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
		
	
	private void setupPopulateMenuPage(Model model, int pageNumber) {
		Dish dishToAdd= new Dish();
		Menu menuToPopulate = ms.getLastMenu();
		EnumSet<DishCategory> categories = EnumSet.allOf(DishCategory.class);
		EnumSet<DishNationality> nationalities = EnumSet.allOf(DishNationality.class);
		
		List<Dish> allDishes = ds.getAllDishes();
		int itemPerPage = 2;
		List<Pageable> pageableDishes = pags.generatePageableList(allDishes.size(), itemPerPage);
		System.out.println(pageableDishes.toString());
		System.out.println(pageNumber);
		
		if(pageableDishes.size() != 0) {
			Page<Dish> dishList = ds.getAllDishesPaginated(pageableDishes.get(pageNumber));
			// List of pages for pagination
			
			model.addAttribute("pagesList", pags.generatePagesList(dishList));
			model.addAttribute("totalPages", dishList.getTotalPages());
			model.addAttribute("dishList", dishList.getContent());
		}else {
			model.addAttribute("pagesList", pags.generatePagesList());
			model.addAttribute("totalPages", "0");
			model.addAttribute("dishList", ds.getAllDishes());
		}
				
		model.addAttribute("menuToPopulate", menuToPopulate);
		model.addAttribute("dishToAdd", dishToAdd);		
		model.addAttribute("categories", categories);
		model.addAttribute("nationalities", nationalities);
		model.addAttribute("pageNumber", pageNumber);
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
