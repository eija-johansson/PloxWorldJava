package se.persandstrom.ploxworld.main;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Asteroid;
import se.persandstrom.ploxworld.locations.AsteroidCreater;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.locations.PlanetCreater;
import se.persandstrom.ploxworld.person.Person;
import se.persandstrom.ploxworld.person.PersonCreater;
import se.persandstrom.ploxworld.production.ProductionType;

import com.google.gson.annotations.Expose;

public class World {

	private static final int SIZE_X = 800;
	private static final int SIZE_Y = 500;

	@Expose private final int height = SIZE_Y;
	@Expose private final int width = SIZE_X;

	@Expose private final Set<Planet> planets;
	@Expose private final Set<Asteroid> asteroids;
	@Expose private final Set<Person> persons;

	@Expose WorldData worldData;
	@Expose int turn = 0;

	public World() {
		PlanetCreater planetCreater = new PlanetCreater(this);
		planets = planetCreater.createPlanets(25);
		AsteroidCreater asteroidCreater = new AsteroidCreater(this);
		asteroids = asteroidCreater.createAsteroids(3);
		PersonCreater characterCreater = new PersonCreater(this);
		persons = characterCreater.createPersons(25);

		planets.forEach(Planet::prepareStuff);
	}

	public void progressTurn() {
		for (Person person : persons) {
			person.getAi().makeDecision(this, person);
		}
		persons.forEach(Person::executeDecision);
		planets.forEach(Planet::progressTurn);
		turn++;
		worldData = new WorldData(this);
	}

	public Point getRandomPoint(int border) {
		return getRandomPoint(border, border, border, border);
	}

	public Point getRandomPoint(int borderTop, int borderRight, int borderBottom, int borderLeft) {
		return new Point(Rand.bound(SIZE_X - borderLeft - borderRight) + borderLeft, Rand.bound(SIZE_Y - borderTop - borderBottom) + borderBottom);
	}

	public Planet getCheapestSellingPlanet(ProductionType productionType) {
		//TODO: Maybe make these return a list of all that are equally good?
		return planets.stream().min((o1, o2) ->
				o1.getProduction(productionType).getSellPrice() - o2.getProduction(productionType).getSellPrice()).get();
	}

	public Planet getMostPayingPlanet(ProductionType productionType) {
		//TODO: Maybe make these return a list of all that are equally good?
		return planets.stream().max((o1, o2) ->
				o1.getProduction(productionType).getBuyPrice() - o2.getProduction(productionType).getBuyPrice()).get();
	}

	public Set<Planet> getPlanets() {
		return planets;
	}

	public Set<Asteroid> getAsteroids() {
		return asteroids;
	}
}
