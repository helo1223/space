//Üzemanyag megjelenítéséért felelõs

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

class Part {
	double value;
	Color color;

	public Part(double value, Color color) {
		this.value = value;
		this.color = color;
	}
}

@SuppressWarnings("serial")
class Fuel extends JComponent {
	Part[] slices = { new Part(0, Color.black), new Part(100, Color.green) };

	public void paint(Graphics g) {
		drawPie((Graphics2D) g, getBounds(), slices);
	}

	public static int G = 255;
	public static int R = 0;

	void drawPie(Graphics2D g, Rectangle area, Part[] slices) { // azért Pie, mert ez egy kördiagram, ne kérdezd miért,
																// mûködik és kész

		double total = 0.0D;
		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}
		double curValue = 0.0D;
		int startAngle = 0;

		Color rgb = new Color(R, G, 0);
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (Hajo.fuel * 360 / Hajo.maxfuel);
			if (Hajo.fuel / Hajo.maxfuel < 0.5) {
				R = 255;
				G = 140;
			}
			if (Hajo.fuel / Hajo.maxfuel < 0.25) {
				R = 255;
				G = 0;
			}
			g.setColor(rgb);
			g.fillArc(Ablak.screenW - 150, Ablak.screenH - 150, 100, 80, startAngle, arcAngle);
			curValue += slices[i].value;
		}
	}
}
