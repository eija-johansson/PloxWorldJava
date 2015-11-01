package se.persandstrom.ploxworld.main;

import java.util.Set;

import se.persandstrom.ploxworld.character.*;
import se.persandstrom.ploxworld.character.Character;
import se.persandstrom.ploxworld.common.Point;
import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Planet;
import se.persandstrom.ploxworld.locations.PlanetCreater;

import com.google.gson.annotations.Expose;

public class World {

	private static final int SIZE_X = 800;
	private static final int SIZE_Y = 500;

	@Expose private final int height = SIZE_Y;
	@Expose private final int width = SIZE_X;

	@Expose private final Set<Planet> planets;
	@Expose private final Set<Character> characters;

	@Expose WorldData worldData;
	@Expose int turn = 0;

	public World() {
		PlanetCreater planetCreater = new PlanetCreater(this);
		planets = planetCreater.createPlanets(25);
		CharacterCreater characterCreater = new CharacterCreater(this);
		characters = characterCreater.createCharacters(25);
	}

	public void progressTurn() {
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

	public Set<Planet> getPlanets() {
		return planets;
	}
}
