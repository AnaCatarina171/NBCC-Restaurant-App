package ca.nbcc.restapp.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MENU_Table")
public class Menu {

	@Id
	@SequenceGenerator(name = "MENU_SEQ_GEN", sequenceName = "MENU_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ_GEN")
	@Column(name = "MENU_ID", unique = true)
	private Long id;
	
	@Column(name="MENU_TITLE")
	private String title;
	
	@Column(name="MENU_DESCRIPTION")
	private String description;
	
	@Column(name="MENU_TYPE")
	private String type;
	
	@Column(name="MENU_DATE")
	private LocalDate date;
	
	@Column(name="MENU_TODISPLAY")
	private Boolean toDisplay; 
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_REST_ID")
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
		return "Menu [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date
				+ ", restaurant=" + restaurant + "]";
	}
	
	
}
