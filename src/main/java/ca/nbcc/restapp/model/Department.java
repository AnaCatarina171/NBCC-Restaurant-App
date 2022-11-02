package ca.nbcc.restapp.model;

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

import ca.nbcc.restapp.model.Department;
import ca.nbcc.restapp.model.Restaurant;

@Entity
@Table(name="Department_Table")
public class Department {

	@Id
	@SequenceGenerator(name = "DEP_SEQ_GEN", sequenceName = "DEP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEP_SEQ_GEN")
	@Column(name = "DEP_ID", unique = true)
	private Long id;
	
	@Column(name="DEP_NAME")
	private String name;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_REST_ID")
	private Restaurant restaurant;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(String name, Restaurant restaurant) {
		super();
		this.name = name;
		this.restaurant = restaurant;
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, restaurant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(restaurant, other.restaurant);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", restaurant=" + restaurant + "]";
	}
}