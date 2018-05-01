package com.gspann.itrack.domain.model.test;

import static com.gspann.itrack.domain.model.test.Burger.BeefPatty.*;
import static com.gspann.itrack.domain.model.test.Burger.Cheese.*;
import static com.gspann.itrack.domain.model.test.Burger.Mushroom.*;

public class Burger {

	private final Patty patty;
	private final Topping topping;

	private Burger(Patty patty, Topping topping) {
		this.patty = patty;
		this.topping = topping;
	}

	public static void main(String[] args) {
		Burger vegBurger = Burger.burger().vegetarian().with(mushroom()).and(cheese());
		System.out.println("vegBurger -->" + vegBurger);
		Burger nonVegBurger = burger().vegetarian().with(beef()).and(cheese());
		System.out.println("nonVegBurger -->" + nonVegBurger);
	}
	
	public static BurgerBuilder burger() {
		return patty -> topping -> new Burger(patty, topping);
	}

	interface BurgerBuilder {
		ToppingBuilder with(Patty patty);

		default VegetarianBuilder vegetarian() {
			return patty -> topping -> new Burger(patty, topping);
		}
	}

	interface ToppingBuilder {
		Burger and(Topping topping);

		default FreeToppingBuilder andFree() {
			return topping -> and(topping);
		}
	}

	interface VegetarianBuilder {
		VegetarianToppingBuilder with(Patty main);
	}

	interface VegetarianToppingBuilder {
		Burger and(VegetarianTopping topping);
	}

	interface FreeToppingBuilder {
		Burger topping(FreeTopping topping);
	}

	interface Patty {
	}

	interface BeefPatty extends Patty {
		public static BeefPatty beef() {
			return null;
		}
	}

	interface VegetarianPatty extends Patty, Vegetarian {
	}

	interface Tofu extends VegetarianPatty {
		public static Tofu tofu() {
			return null;
		}
	}

	interface Mushroom extends VegetarianPatty {
		public static Mushroom mushroom() {
			return null;
		}
	}

	interface Topping {
	}

	interface VegetarianTopping extends Vegetarian, Topping {
	}

	interface FreeTopping extends Topping {
	}

	interface Bacon extends Topping {
		public static Bacon bacon() {
			return null;
		}
	}

	interface Tomato extends VegetarianTopping, FreeTopping {
		public static Tomato tomato() {
			return null;
		}
	}

	interface Cheese extends VegetarianTopping {
		public static Cheese cheese() {
			return null;
		}
	}

	interface Omnivore extends Vegetarian {
	}

	interface Vegetarian extends Vegan {
	}

	interface Vegan extends DietaryChoice {
	}

	interface DietaryChoice {
	}
}
