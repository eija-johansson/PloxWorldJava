package se.persandstrom.ploxworld.ship;

import org.junit.Test;

import se.persandstrom.ploxworld.production.ProductionType;

import static org.junit.Assert.*;

public class ShipTest {

	@Test
	public void storage() {
		Ship ship = new Ship();
		assertEquals(Ship.MAX_STORAGE, ship.getFreeStorage());

		ship.addStorage(ProductionType.COMMODITY, 10);
		assertEquals(Ship.MAX_STORAGE - 10, ship.getFreeStorage());

		Integer commodity = ship.getStorage(ProductionType.COMMODITY);
		assertEquals(10, commodity.intValue());

		ship.addStorage(ProductionType.CRYSTAL, 15);
		assertEquals(Ship.MAX_STORAGE - 25, ship.getFreeStorage());
	}

}