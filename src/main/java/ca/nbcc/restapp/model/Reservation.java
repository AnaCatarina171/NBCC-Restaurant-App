package ca.nbcc.restapp.model;

import java.time.LocalDateTime;
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
@Table(name="Reservation_Table")
public class Reservation {

	@Id
	@SequenceGenerator(name = "RES_SEQ_GEN", sequenceName = "RES_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_SEQ_GEN")
	@Column(name = "RES_ID", unique = true)
	private Long id;
	
	@Column(name="RES_TRACKING_NUMBER")
	private String trackingNumber;
	
	@Column(name="RES_DATETIME")
	private LocalDateTime dateTime;
	
	@Column(name="RES_ADULTS")
	private int adults;
	
	@Column(name="RES_KIDS")
	private int kids;
	
	@Column(name="RES_VEGETARIAN")
	private int vegetarian;
	
	@Column(name="RES_VEGAN")
	private int vegan;
	
	@Column(name="RES_WHEEL_CHAIR")
	private int wheelChair;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_REST_ID")
	private Restaurant restaurant;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_CUST_ID")
	private Customer customer;

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(String trackingNumber, LocalDateTime dateTime, int adults, int kids, int vegetarian, int vegan,
			int wheelChair, Restaurant restaurant, Customer customer) {
		super();
		this.trackingNumber = trackingNumber;
		this.dateTime = dateTime;
		this.adults = adults;
		this.kids = kids;
		this.vegetarian = vegetarian;
		this.vegan = vegan;
		this.wheelChair = wheelChair;
		this.restaurant = restaurant;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public int getVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(int vegetarian) {
		this.vegetarian = vegetarian;
	}

	public int getVegan() {
		return vegan;
	}

	public void setVegan(int vegan) {
		this.vegan = vegan;
	}

	public int getWheelChair() {
		return wheelChair;
	}

	public void setWheelChair(int wheelChair) {
		this.wheelChair = wheelChair;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adults, customer, dateTime, id, kids, restaurant, trackingNumber, vegan, vegetarian,
				wheelChair);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return adults == other.adults && Objects.equals(customer, other.customer)
				&& Objects.equals(dateTime, other.dateTime) && Objects.equals(id, other.id) && kids == other.kids
				&& Objects.equals(restaurant, other.restaurant) && Objects.equals(trackingNumber, other.trackingNumber)
				&& vegan == other.vegan && vegetarian == other.vegetarian && wheelChair == other.wheelChair;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", trackingNumber=" + trackingNumber + ", dateTime=" + dateTime + ", adults="
				+ adults + ", kids=" + kids + ", vegetarian=" + vegetarian + ", vegan=" + vegan + ", wheelChair="
				+ wheelChair + ", restaurant=" + restaurant + ", customer=" + customer + "]";
	}

	
}
