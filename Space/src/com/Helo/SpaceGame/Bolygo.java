//Bolygók generálásáért, és betöltéséért felelõs

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.xml.bind.DatatypeConverter;

public class Bolygo extends Shape {
	public static Image planet;
	private static int id, fact;
	static int xB,yB;
	private static int mineral;
	private static int minelvl;
	private static int barrackslvl;
	private static int baselvl;
	private static int wallslvl;
	private static String name;
	public static String loaded = "";
	public static String[] plantest = new String[30];
	public static String[][] plansplit = new String[30][10];
	final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
	static boolean saveexists;
	public static String[] codedplan = new String[30];
	public static String[][]codedsplit = new String[30][10];

	final static java.util.Random rand = new java.util.Random();

	final static Set<String> identifiers = new HashSet<String>();

	public Bolygo() {
		URL planetU = Board.class.getResource("/resource/planet2.gif");
		ImageIcon planetI = new ImageIcon(planetU);
		planet = planetI.getImage();

	}

	// Generálás
	public static void initplanet() {
		for (int i = 0; i < 10; i++) { // Szövetséges bolygók
			id = i;
			name = randomIdentifier();
			fact = 1;
			xB = (int) (Math.random() * (3000-100)+100);
			yB = (int) (Math.random() * 5000 + 500);
			mineral=0;
			baselvl=1;
			minelvl=0;
			barrackslvl=0;
			wallslvl=0;
			// id | name | faction | x | y | mineraldb | baselvl | minelvl | barrackslvl | wallslvl
			plantest[i] = id + ";" + name + ";" + fact + ";" + xB + ";" + yB + ";"+mineral+";"+baselvl+";"+minelvl+";"+barrackslvl+";"+wallslvl; // Generált adatok egybefogása
			plansplit[i] = plantest[i].split(";"); // Majd azok feldarabolása mátrixba
		}
		for (int i = 10; i < 20; i++) { // Semleges bolygók
			id = i;
			name = randomIdentifier();
			fact = 0;
			xB = (int) (Math.random() * (4000-3000)+3000);
			yB = (int) (Math.random() * 5000 + 500);
			mineral=0;
			baselvl=0;
			minelvl=0;
			barrackslvl=0;
			wallslvl=0;
			// id | name | faction | x | y |
			plantest[i] = id + ";" + name + ";" + fact + ";" + xB + ";" + yB + ";"+mineral+";"+baselvl+";"+minelvl+";"+barrackslvl+";"+wallslvl; // Generált adatok egybefogása
			plansplit[i] = plantest[i].split(";"); // Majd azok feldarabolása mátrixba
			}
		for (int i = 20; i < 30; i++) {
			id = i;
			name = randomIdentifier();
			fact = 2;
			xB = (int) (Math.random() * (7500-5000)+5000);
			yB = (int) (Math.random() * 5000 + 500);
			mineral=0;
			baselvl=1;
			minelvl=0;
			barrackslvl=0;
			wallslvl=0;
			// id | name | faction | x | y |
			plantest[i] = id + ";" + name + ";" + fact + ";" + xB + ";" + yB + ";"+mineral+";"+baselvl+";"+minelvl+";"+barrackslvl+";"+wallslvl; // Generált adatok egybefogása
			plansplit[i] = plantest[i].split(";"); // Majd azok feldarabolása mátrixba
			
		}
		
		for (int i = 0; i < 30; i++) {
			PlanetInterface.ids[i]=Integer.parseInt(plansplit[i][0]);

		}

	}
	
	public static void checkplanet() {
		for (int i = 0; i < 3; i++) {
			
		for (int j = 20; j < 30; j++) {
			if(Bolygo.xB <= Integer.parseInt(Bolygo.plansplit[j][3])+300 && Bolygo.xB >= Integer.parseInt(Bolygo.plansplit[j][3])-300 && (Bolygo.yB <= Integer.parseInt(Bolygo.plansplit[j][4])+300 && Bolygo.yB >= Integer.parseInt(Bolygo.plansplit[j][4])-300)) {
				Bolygo.xB = (int) (Math.random() * (7500-5000)+5000);
				Bolygo.plansplit[j][3]=Bolygo.xB+"";
				Bolygo.yB = (int) (Math.random() * (5000-500)+500);
				Bolygo.plansplit[j][4]=Bolygo.yB+"";
			}
		}
		for (int j = 0; j < 10; j++) {
			if(Bolygo.xB <= Integer.parseInt(Bolygo.plansplit[j][3])+300 && Bolygo.xB >= Integer.parseInt(Bolygo.plansplit[j][3])-300 && (Bolygo.yB <= Integer.parseInt(Bolygo.plansplit[j][4])+300 && Bolygo.yB >= Integer.parseInt(Bolygo.plansplit[j][4])-300)) {
				Bolygo.xB = (int) (Math.random() * (3000-100)+100);
				Bolygo.plansplit[j][3]=Bolygo.xB+"";
				Bolygo.yB = (int) (Math.random() * (5000-500)+500);
				Bolygo.plansplit[j][4]=Bolygo.yB+"";
			}
		}
		for (int j = 10; j < 20; j++) {
			if(Bolygo.xB <= Integer.parseInt(Bolygo.plansplit[j][3])+300 && Bolygo.xB >= Integer.parseInt(Bolygo.plansplit[j][3])-300 && (Bolygo.yB <= Integer.parseInt(Bolygo.plansplit[j][4])+300 && Bolygo.yB >= Integer.parseInt(Bolygo.plansplit[j][4])-300)) {
				Bolygo.xB = (int) (Math.random() * (4000-3000)+3000);
				Bolygo.plansplit[j][3]=Bolygo.xB+"";
				Bolygo.yB = (int) (Math.random() * (5000-500)+500);
				Bolygo.plansplit[j][4]=Bolygo.yB+"";
			}
		}
		}
	}

	// Bolygó adatok betöltése fájlból
	public static void loadplanet() {
		String b64;
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader("planets.save");
			br = new BufferedReader(fr);
			br.readLine();
			for (int i = 0; i < 30; i++) {
				b64 = br.readLine();
				codedplan[i]=b64;
				codedsplit[i]=codedplan[i].split(";");
				for (int j = 0; j < 10; j++) {
					loaded = new String(DatatypeConverter.parseBase64Binary(codedsplit[i][j]));
					plansplit[i][j]=loaded;
				}

			}
			saveexists = true;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			saveexists = false;
			System.err.println("Bolygó mentés nem található");
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	// Bolygónév generátor
	public static String randomIdentifier() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	// Bolygók megjelenítéséért felelõs rajzoló metódus
	public void draw(Graphics g) {
		for (int j = 0; j < 30; j++) {
			Color whitetrans = new Color(255, 255, 255, 60);
			Color greentrans = new Color(0, 255, 0, 60);
			Color redtrans = new Color(255, 0, 0, 60);
			if (Integer.parseInt(plansplit[j][2]) == 0) {
				g.setColor(whitetrans);
			} else if (Integer.parseInt(plansplit[j][2]) == 1) {
				g.setColor(greentrans);
			} else if (Integer.parseInt(plansplit[j][2]) == 2) {
				g.setColor(redtrans);
			}
			g.fillOval(Integer.parseInt(plansplit[j][3]) - 75, Integer.parseInt(plansplit[j][4]) - 75, 200, 200);

		}
		for (int i = 0; i < 30; i++) {
			if (Integer.parseInt(plansplit[i][2]) == 0) {
				g.setColor(Color.WHITE);
			} else if (Integer.parseInt(plansplit[i][2]) == 1) {
				g.setColor(Color.GREEN);
			} else if (Integer.parseInt(plansplit[i][2]) == 2) {
				g.setColor(Color.RED);
			}
			g.drawImage(planet, Integer.parseInt(plansplit[i][3]), Integer.parseInt(plansplit[i][4]), 50, 50, null);
			g.drawString(plansplit[i][1], Integer.parseInt(plansplit[i][3]), Integer.parseInt(plansplit[i][4]) + 60);
			g.drawRect(Integer.parseInt(plansplit[i][3])-120, Integer.parseInt(plansplit[i][4])-120, 300, 300);
		}

	}
}
