//A háttér megjelenítéséért felel
//Random generál 1000 csillagot, amiket elhelyez a világban

package com.Helo.SpaceGame;

import java.awt.Graphics;
import java.util.ArrayList;

public class Background {
	int x, y, r;
	ArrayList<Shape> stars = new ArrayList<Shape>();

	public Background() {
		initComponents();
	}

	private void initComponents() {
		// Bolygo.initplanet();
		for (int i = 0; i < 1000; i++) { // 1000db csillag
			x = (int) (Math.random() * 8000); // X koordináta lehet 0-8000
			y = (int) (Math.random() * 6000); // Y koordináta lehet 0-6000
			r = (int) (Math.random() * 10); // csillag átmérõje lehet 0-10
			stars.add(new Stars(x, y, r)); // hozzáadja a csillagok listájához a generált csillagot

		}

	}

	// Rajzoló metódus
	public void paint(Graphics g) {
		for (Shape s : stars) {
			s.draw(g);
		}
	}
}
