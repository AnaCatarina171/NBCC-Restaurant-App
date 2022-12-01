package ca.nbcc.restapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	
	@GetMapping("/recallMenu/{mId}")
	public String toRecallMenu(Model model,  @PathVariable("mId") long mId) {
		setupRecallMenuPage(model, 0, mId); 
		return "user-recallMenu";
	}
	
	

	@GetMapping("/toCancelMenu")
	public String toCancelMenu(Model model) {
		try {
			long menuId = ms.getLastMenu().getId();
			
			ms.deleteMenu(menuId);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/toMenuPanel";
	}
	
	@GetMapping("/deleteDish/{dId}")
	public String toDeleteDish(Model model, @PathVariable("dId") long dId) {
		try {
			ds.deleteDish(dId);
			setupPopulateMenuPage(model, 0);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "user-populateMenu";
	}
	
	@GetMapping("/toDeleteMenu")
	public String toDeleteMenu(Model model, @RequestParam("menuId") String menuId) {
		try {
			System.out.println(menuId);
			long longMenuId = Long.parseLong(menuId);

			ms.deleteMenu(longMenuId);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/toMenuPanel";
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
	
	@GetMapping("/toSaveMenu/{mId}")
	public String toSaveMenu(Model model, @PathVariable("mId") long mId) {
		Menu menuToSave = ms.getMenuById(mId);
		System.out.println(menuToSave.getDishList().toString());
		menuToSave.setDate(LocalDate.now());
		ms.saveMenu(menuToSave);
		
		return "redirect:/toMenuPanel";
	}
	
	
	
	@PostMapping("/processAddNewItem")
	public String processAddNewItem(Model model, Dish dishToAdd) {
		Menu menuToPopulate = ms.getLastMenu();

		//Changed the variable to a Set since it doesn't allow duplicates
		Set<Menu> newMenuSet = new HashSet<Menu>();
		newMenuSet.add(menuToPopulate);
		dishToAdd.setMenuSet(newMenuSet);
		ds.addNewDish(dishToAdd);
		
		Set<Dish> newMenuDishSet = new HashSet<Dish>();
		newMenuDishSet.add(dishToAdd);
		menuToPopulate.setDishList(newMenuDishSet);
		ms.saveMenu(menuToPopulate);

		setupPopulateMenuPage(model, 0);
		return "user-populateMenu";
	}
	
	@GetMapping("/addDishToMenu/{dId}")
	public String addDishToMenu(Model model, @PathVariable("dId") long rId) {
		Menu menuToPopulate = ms.getLastMenu();
		System.out.println(menuToPopulate.toString());
		Set<Dish> menuDishList = menuToPopulate.getDishList();
		System.out.println(menuDishList.toString());
		menuDishList.add(ds.getDishById(rId));
		System.out.println(menuDishList.toString());
		menuToPopulate.setDishList(menuDishList);
		ms.saveMenu(menuToPopulate);
		
		setupPopulateMenuPage(model, 0);
		return "user-populateMenu";
	}
		
	@GetMapping("/addDishToRecalledMenu/{dId}/{mId}")
	public String addDishToRecalledMenu(Model model, @PathVariable("dId") long dId,
			@PathVariable("mId") long mId) {	
		Menu menuToPopulate = ms.getMenuById(mId);
		Set<Dish> menuDishList = menuToPopulate.getDishList();

		menuDishList.add(ds.getDishById(dId));
		System.out.println(menuDishList.toString());
		
		menuToPopulate.setDishList(menuDishList);
		System.out.println(menuToPopulate.getDishList().toString());
		ms.saveMenu(menuToPopulate);
		
		setupRecallMenuPage(model, 0, mId);
		return "user-recallMenu";
	}
	
	// Dish Actions
	@GetMapping("/deleteDishRecalled/{dId}/{mId}")
	public String deleteDishRecalled(Model model, @PathVariable("dId") long dId,
			@PathVariable("mId") long mId) {
		ds.deleteDish(dId);
		setupRecallMenuPage(model, 0, mId);
		return "user-recallMenu";
	}
	
	private void setupPopulateMenuPage(Model model, int pageNumber) {
		setupPagination(model, pageNumber, 10);
		setupDishPanel(model);
		
		Menu menuToPopulate = ms.getLastMenu();		
		
		model.addAttribute("menuToPopulate", menuToPopulate);
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
	
	
	private void setupRecallMenuPage(Model model, int pageNumber, long mId) {		
		setupPagination(model, pageNumber, 10);
		setupDishPanel(model);
		
		Menu menuToPopulate = ms.getMenuById(mId);		
		
		model.addAttribute("menuToPopulate", menuToPopulate);
		model.addAttribute("pageNumber", pageNumber);
	}
	
	private void setupDishPanel(Model model) {
		Dish dishToAdd= new Dish();		
		EnumSet<DishCategory> categories = EnumSet.allOf(DishCategory.class);
		EnumSet<DishNationality> nationalities = EnumSet.allOf(DishNationality.class);
				
		model.addAttribute("dishToAdd", dishToAdd);		
		model.addAttribute("categories", categories);
		model.addAttribute("nationalities", nationalities);
	}
	
	private void setupPagination(Model model, int pageNumber, int itemPerPage) {
		List<Dish> allDishes = ds.getAllDishes();
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
	}
}
