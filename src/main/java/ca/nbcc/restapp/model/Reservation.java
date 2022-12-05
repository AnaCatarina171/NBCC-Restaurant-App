package ca.nbcc.restapp.model;

import java.time.LocalDateTime;
import java.util.Date;
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Reservation_Table")
public class Reservation {

	@Id
	@SequenceGenerator(name = "RES_SEQ_GEN", sequenceName = "RES_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_SEQ_GEN")
	@Column(name = "RES_ID", unique = true)
	private Long id;
	
	@Column(name="RES_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@Column(name="RES_TIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String time;
	
	@Column(name="RES_GUESTS")
	private int guests;
	
	@Column(name="RES_ADULTS")
	private int adults;
	
	@Column(name="RES_KIDS")
	private int kids;
	
	@Column(name="RES_VEGETARIAN")
	private int vegetarian;
	
	@Column(name="RES_VEGAN")
	private int vegan;
	
	@Column(name="RES_WHEEL_CHAIR")
	private boolean wheelChair;
	
	@Column(name="RES_INFO")
	private String additionalInfo;
	
	@Column(name="RES_STATUS")
	private ReservationStatus status;
	
	@Column(name="RES_FNAME")
	private String firstName;
	
	@Column(name="RES_LNAME")
	private String lastName;
	
	@Column(name="RES_PHONE")
	private String phone;
	
	@Column(name="RES_EMAIL")
	private String email;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_REST_ID")
	private Restaurant restaurant;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_CUST_ID")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_TABLE_ID")
	private RTable table;

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(Date dateTime, int adults, int kids, int vegetarian, int vegan,
			boolean wheelChair, Restaurant restaurant, Customer customer) {
		super();
		this.date = dateTime;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date dateTime) {
		this.date = dateTime;
	}

	
	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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

	public boolean getWheelChair() {
		return wheelChair;
	}

	public void setWheelChair(boolean wheelChair) {
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RTable getTable() {
		return table;
	}

	public void setTable(RTable table) {
		this.table = table;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adults, customer, date, id, kids, restaurant, vegan, vegetarian,
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
				&& Objects.equals(date, other.date) && Objects.equals(id, other.id) && kids == other.kids
				&& Objects.equals(restaurant, other.restaurant)
				&& vegan == other.vegan && vegetarian == other.vegetarian && wheelChair == other.wheelChair;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", dateTime=" + date + ", adults="
				+ adults + ", kids=" + kids + ", vegetarian=" + vegetarian + ", vegan=" + vegan + ", wheelChair="
				+ wheelChair + ", restaurant=" + restaurant + ", customer=" + customer + "]";
	}

	
}
