//Hajó adataiért felelõs

package com.Helo.SpaceGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

public class Hajo {

	public static double maxfuel = 100;
	public static double fuel = 100;
	public static double hp = 100;
	public static boolean saveexists;
	public static double gold = 1000;

	public Hajo() {
	}

	// Hajó adatok betöltése fájlból
	public static void loadShip() {
		String b64;
		String loaded;
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader("ship.save");
			br = new BufferedReader(fr);
			b64=br.readLine();
			loaded = new String(DatatypeConverter.parseBase64Binary(b64));
			maxfuel = Double.parseDouble(loaded);
			b64=br.readLine();
			loaded = new String(DatatypeConverter.parseBase64Binary(b64));
			fuel = Double.parseDouble(loaded);
			b64=br.readLine();
			loaded = new String(DatatypeConverter.parseBase64Binary(b64));
			hp = Double.parseDouble(loaded);
			saveexists = true;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			saveexists = false;
			System.err.println("Hajó mentés nem található");
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Ellenõrzi, hogy mozog-e a hajó
	public static boolean checkMoving() {
		Board.getValami();
		// Ha mozog, fogyjon az üzemanyag
		if (Board.getValami() != false) {
			fuel -= 0.01;
		}
		// Nincs üzemanyag, nincs mozgás
		if (fuel <= 0) {
			Board.valami = false;
			fuel = 0;
		}
		return Board.getValami();
	}

	public static void newShip() {
		maxfuel=100;
		fuel=100;
		hp=100;
		gold=1000;
		Fuel.R=0;
		Fuel.G=255;
	}
}
