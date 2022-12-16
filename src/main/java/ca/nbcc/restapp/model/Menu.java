package ca.nbcc.restapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MENU_Table")
public class Menu {

	@Id
	@SequenceGenerator(name = "MENU_SEQ_GEN", sequenceName = "MENU_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ_GEN")
	@Column(name = "MENU_ID", unique = true)
	private Long id;

	@Column(name = "MENU_TITLE")
	private String title;

	@Column(name = "MENU_DESCRIPTION")
	private String description;

	@Column(name = "MENU_TYPE")
	private String type;

	@Column(name = "MENU_DATE")
	private LocalDate date;

	@Column(name = "MENU_TODISPLAY")
	private Boolean toDisplay;

	// @OneToMany(mappedBy="menu", cascade = CascadeType.ALL, orphanRemoval = true)
	@ManyToMany
	@JoinTable(name = "menu_dishes", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
	private List<Dish> dishList;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "FK_REST_ID")
	private Restaurant restaurant;

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu(String title, String description, LocalDate date, Restaurant restaurant, Boolean toDisplay) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
		this.restaurant = restaurant;
		this.toDisplay = toDisplay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Boolean getToDisplay() {
		return toDisplay;
	}

	public void setToDisplay(Boolean toDisplay) {
		this.toDisplay = toDisplay;
	}

	public List<Dish> getDishList() {
		Collections.sort(dishList);
		return dishList;
	}

	public void setDishList(List<Dish> dishList) {
		this.dishList = dishList;
	}

	public List<Dish> getAppList() {
		List<Dish> appList = new ArrayList<>();

		for (Dish dish : this.getDishList()) {
			if (dish.getCategory() == DishCategory.Appetizer) {
				appList.add(dish);
			}
		}

		return appList;
	}

	public List<Dish> getEntreeList() {
		List<Dish> entreeList = new ArrayList<>();

		for (Dish dish : this.getDishList()) {
			if (dish.getCategory() == DishCategory.Entree) {
				entreeList.add(dish);
			}
		}

		return entreeList;
	}

	public List<Dish> getDessertList() {
		List<Dish> dessertList = new ArrayList<>();

		for (Dish dish : this.getDishList()) {
			if (dish.getCategory() == DishCategory.Dessert) {
				dessertList.add(dish);
			}
		}

		return dessertList;
	}

	public List<Dish> getDrinkList() {
		List<Dish> drinkList = new ArrayList<>();

		for (Dish dish : this.getDishList()) {
			if (dish.getCategory() == DishCategory.Drink) {
				drinkList.add(dish);
			}
		}

		return drinkList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, description, id, restaurant, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		return Objects.equals(date, other.date) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(restaurant, other.restaurant)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", title=" + title + ", description=" + description + ", type=" + type + ", date="
				+ date + ", toDisplay=" + toDisplay + ", dishList=" + dishList + ", restaurant=" + restaurant + "]";
	}

}
