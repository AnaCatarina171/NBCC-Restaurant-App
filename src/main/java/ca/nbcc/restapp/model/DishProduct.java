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

@Entity
@Table(name="Dish_Product_Table")
public class DishProduct {

	@Id
	@SequenceGenerator(name = "DPROD_SEQ_GEN", sequenceName = "DPROD_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DPROD_SEQ_GEN")
	@Column(name = "DPROD_ID", unique = true)
	private Long id;
	
	@Column(name="DPROD_QUANTITY")
	private int quantity;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_DISH_ID")
	private Dish dish;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_PROD_ID")
	private Product product;

	public DishProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DishProduct(int quantity, Dish dish, Product product) {
		super();
		this.quantity = quantity;
		this.dish = dish;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dish, id, product, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DishProduct other = (DishProduct) obj;
		return Objects.equals(dish, other.dish) && Objects.equals(id, other.id)
				&& Objects.equals(product, other.product) && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "DishProduct [id=" + id + ", quantity=" + quantity + ", dish=" + dish + ", product=" + product + "]";
	}
	
	
}
