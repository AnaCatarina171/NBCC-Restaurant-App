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
@Table(name="Floor_Plan_Table")
public class FloorPlan {

	@Id
	@SequenceGenerator(name = "FLOOR_PLAN_SEQ_GEN", sequenceName = "FLOOR_PLAN_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLOOR_PLAN_SEQ_GEN")
	@Column(name = "FLOOR_PLAN_ID", unique = true)
	private Long id;
	
	@Column(name="FLOOR_PLAN_DATE")
	private LocalDate date;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_REST_ID")
	private Restaurant restaurant;

	public FloorPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FloorPlan(LocalDate date, Restaurant restaurant) {
		super();
		this.date = date;
		this.restaurant = restaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return Objects.hash(date, id, restaurant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloorPlan other = (FloorPlan) obj;
		return Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(restaurant, other.restaurant);
	}

	@Override
	public String toString() {
		return "FloorPlan [id=" + id + ", date=" + date + ", restaurant=" + restaurant + "]";
	}
	
	
}
