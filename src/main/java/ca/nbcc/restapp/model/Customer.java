package ca.nbcc.restapp.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Customer_Table")
public class Customer {

	@Id
	@SequenceGenerator(name = "CUS_SEQ_GEN", sequenceName = "CUS_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUS_SEQ_GEN")
	@Column(name = "CUS_ID", unique = true)
	private Long id;
	
	@Column(name="CUS_USERNAME")
	private String username;
	
	@Column(name="CUS_PASSWORD")
	private String password;
	
	@Column(name="CUS_FNAME")
	private String firstName;
	
	@Column(name="CUS_LNAME")
	private String lastName;
	
	@Column(name="CUS_PHONE")
	private String phone;
	
	@Column(name="CUS_EMAIL")
	private String email;
	
	@Column(name="CUS_ADDRESS")
	private String address;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String username, String password, String firstName, String lastName, String phone, String email,
			String address) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, email, firstName, id, lastName, password, phone, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", address=" + address + "]";
	}
	
}
