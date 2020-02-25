package com.Helo.SpaceGame;

import java.awt.Graphics;

public abstract class Shape {
	private int x;
	private int y;
	private int rad;

	public Shape() {
		this(0, 0, 1);
	}

	public Shape(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
	}

	public abstract void draw(Graphics g);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRad() {
		return rad;
	}

}