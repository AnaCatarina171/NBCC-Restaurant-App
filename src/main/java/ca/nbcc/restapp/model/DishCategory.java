package ca.nbcc.restapp.model;

public enum DishCategory {
	Appetizer(0),
	Entree(1),
	Dessert(2),
	Drink(3);

	private final int categoryValue;
	
	DishCategory(int categoryValue) {
		this.categoryValue = categoryValue;
	}

	public int getCategoryValue() {
		return categoryValue;
	}
}
