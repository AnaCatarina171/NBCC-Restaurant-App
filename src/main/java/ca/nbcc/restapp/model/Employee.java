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
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@Scope("prototype")
@Entity
@Table(name="Employee_Table")
public class Employee {

	@Id
	@SequenceGenerator(name = "EMP_SEQ_GEN", sequenceName = "EMP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ_GEN")
	@Column(name = "EMP_ID", unique = true)
	private Long id;
	
	@NotNull
	@Column(name="EMP_USERNAME", unique=true)
	private String username;
	
	@Column(name="EMP_PASSWORD")
	private String password;
	
	@Column(name="EMP_FNAME")
	private String firstName;
	
	@Column(name="EMP_LNAME")
	private String lastName;
	
	@Column(name="EMP_PHONE")
	private String phone;
	
	@Column(name="EMP_EMAIL")
	private String email;
	
	@Column(name="EMP_ADDRESS")
	private String address;
	
	@Column(name="EMP_ROLE")
	private EmployeeRole role;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_DEP_ID")
	private Department department;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String username, String password, String firstName, String lastName, String phone, String email,
			String address, EmployeeRole role, Department department) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.role = role;
		this.department = department;
	}
	
	public Employee(Employee emp) {
		super();
		this.username = emp.getUsername();
		this.password = emp.getPassword();
		this.firstName = emp.getFirstName();
		this.lastName = emp.getLastName();
		this.phone = emp.getPhone();
		this.email = emp.getEmail();
		this.address = emp.getAddress();
		this.role = emp.getRole();
		this.department = emp.getDepartment();
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

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, department, email, firstName, id, lastName, password, phone, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(address, other.address) && Objects.equals(department, other.department)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone) && role == other.role
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", address=" + address
				+ ", role=" + role + ", department=" + department + "]";
	}
}
