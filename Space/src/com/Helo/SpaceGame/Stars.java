//Háttérben található csillagok legenerálásáért felelõs

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Graphics;

public class Stars extends Shape {
	public Stars(int x, int y, int rad) {
		super(x, y, rad);
	}

	public Stars() {
		super();
	}

	public void draw(Graphics g) {
		Color color = new Color(255, 255, 255, 200);
		g.setColor(color);
		g.fillOval(getX() - 2, getY() - 2, getRad() + 4, getRad() + 4);
		g.setColor(Color.WHITE);
		g.fillOval(getX(), getY(), getRad(), getRad());

	}
}