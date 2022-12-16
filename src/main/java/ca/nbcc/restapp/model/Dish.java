package ca.nbcc.restapp.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Dish_Table")
public class Dish  implements Comparable<Dish>{

	@Id
	@SequenceGenerator(name = "DISH_SEQ_GEN", sequenceName = "DISH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISH_SEQ_GEN")
	@Column(name = "DISH_ID", unique = true)
	private Long id;
	
	@Column(name="DISH_NAME")
	private String name;
	
	@Column(name="DISH_DESCRIPTION")
	private String description;
	
	@Column(name="DISH_NATIONALITY")
	private DishNationality nationality;
	
	@Column(name="DISH_CATEGORY")
	private DishCategory category;
	
	//@ManyToOne(cascade = CascadeType.MERGE)
	//@JoinColumn(name="FK_MENU_ID")
	@ManyToMany(mappedBy="dishList")
	private Set<Menu> menu;

	public Dish(String name, String description, DishNationality nationality, DishCategory category) {
		super();
		this.name = name;
		this.description = description;
		this.nationality = nationality;
		this.category = category;
	}

	public Dish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DishNationality getNationality() {
		return nationality;
	}

	public void setNationality(DishNationality nationality) {
		this.nationality = nationality;
	}

	public DishCategory getCategory() {
		return category;
	}

	public void setCategory(DishCategory category) {
		this.category = category;
	}

	public Set<Menu> getMenuSet() {
		return menu;
	}

	public void setMenuSet(Set<Menu> menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, description, id, menu, name, nationality);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		return category == other.category && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(menu, other.menu) && Objects.equals(name, other.name)
				&& nationality == other.nationality;
	}

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", nationality=" + nationality
				+ ", category=" + category + ", menu=" + menu + "]";
	}

	@Override
	public int compareTo(Dish o) {
		return category.getCategoryValue() - o.getCategory().getCategoryValue();
	}
}
