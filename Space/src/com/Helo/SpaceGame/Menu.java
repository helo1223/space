//Fõmenü felépítéséért felelõs

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Menu {

	private static final int XPos = 0;
	public static Rectangle newButton;
	public static Rectangle loadButton;
	public static Rectangle quitButton;

	// Gombok meg ilyesmi, nem érdekes
	public void render(Graphics g) {
		newButton = new Rectangle((Ablak.screenW / 2) - 175, 140, 350, 60);
		loadButton = new Rectangle((Ablak.screenW / 2) - 175, 240, 350, 60);
		quitButton = new Rectangle((Ablak.screenW / 2) - 125, 340, 250, 60);
		Toolkit.getDefaultToolkit().sync();
		Graphics2D g2d = (Graphics2D) g;

		Font font1 = new Font("arial", Font.BOLD, 50);
		g.setFont(font1);
		g.setColor(Color.WHITE);
		String T = "ÛR JÁTÉK";
		int stringLenT = (int) g2d.getFontMetrics().getStringBounds(T, g2d).getWidth();
		int widthT = Ablak.screenW;
		int startT = widthT / 2 - stringLenT / 2;
		g2d.drawString(T, startT + XPos, 100);

		String P = "ÚJ JÁTÉK";
		int stringLenP = (int) g2d.getFontMetrics().getStringBounds(P, g2d).getWidth();
		int widthP = Ablak.screenW;
		int startP = widthP / 2 - stringLenP / 2;
		g2d.drawString(P, startP + XPos, 200 - 7);

		if (Bolygo.saveexists == false && Hajo.saveexists == false && Board.saveexists == false) {
			g.setColor(Color.gray);
		}
		String H = "FOLYTATÁS";
		int stringLenH = (int) g2d.getFontMetrics().getStringBounds(H, g2d).getWidth();
		int widthH = Ablak.screenW;
		int startH = widthH / 2 - stringLenH / 2;
		g2d.drawString(H, startH + XPos, 300 - 7);
		g.setColor(Color.white);
		String Q = "KILÉPÉS";
		int stringLenQ = (int) g2d.getFontMetrics().getStringBounds(Q, g2d).getWidth();
		int widthQ = Ablak.screenW;
		int startQ = widthQ / 2 - stringLenQ / 2;
		g2d.drawString(Q, startQ + XPos, 400 - 7);
		g2d.draw(newButton);
		g2d.draw(loadButton);
		g2d.draw(quitButton);
	}
}