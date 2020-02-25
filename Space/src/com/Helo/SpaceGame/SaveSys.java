//Menés/Betöltés rendszerért felelõs

package com.Helo.SpaceGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;


import com.Helo.SpaceGame.Board.STATE;

public class SaveSys {
	
	final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

	

	public static void checkFile() { // Fájlok létezését ellenõrzõ metódus
		File bolygosave = new File("planets.save");
		File hajosave = new File("ship.save");
		File playersave = new File("player.save");

		if (bolygosave.exists()) {
			Bolygo.saveexists = true;
		}
		if (hajosave.exists()) {
			Hajo.saveexists = true;
		}
		if (playersave.exists()) {
			Board.saveexists = true;
		}
	}

	public static void Save() { // Mentés
		String b64;
		try {
			FileWriter fw = new FileWriter("planets.save");
			fw.write("ID | NAME | FACTION | X | Y | MINERAL | BASELVL | MINELVL | BARRACKSLVL | WALLSLVL");
			fw.write("\r\n");
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 10; j++) {
					b64=DatatypeConverter.printBase64Binary(Bolygo.plansplit[i][j].getBytes());
					fw.write(b64);
					fw.write(";");
				}
				fw.write("\r\n");
			}

			FileWriter fw2 = new FileWriter("ship.save");
			b64=DatatypeConverter.printBase64Binary(Double.toString(Hajo.maxfuel).getBytes());
			fw2.write(b64);
			fw2.write("\r\n");
			b64 =DatatypeConverter.printBase64Binary(Double.toString(Hajo.fuel).getBytes());
			fw2.write(b64);
			fw2.write("\r\n");
			b64 = DatatypeConverter.printBase64Binary(Double.toString(Hajo.hp).getBytes());
			fw2.write(b64);

			FileWriter fw3 = new FileWriter("player.save");
			b64=DatatypeConverter.printBase64Binary(Integer.toString(Board.playerX).getBytes());
			fw3.write(b64);
			fw3.write("\r\n");
			b64=DatatypeConverter.printBase64Binary(Integer.toString(Board.playerY).getBytes());
			fw3.write(b64);
			Bolygo.saveexists = true;
			Hajo.saveexists = true;
			Board.saveexists = true;
			fw.close();
			fw2.close();
			fw3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void load() { // Betöltés
		if (Bolygo.saveexists == true && Hajo.saveexists == true && Board.saveexists == true) { // Csak akkor lehet
																								// betölteni, ha minden
																								// szükséges fájl
																								// létezik
			Bolygo.loadplanet();
//			PlanetInterface.buildingBool=false;
			Board.stopmovement();
			Hajo.loadShip();
			Board.loadPlayer();
			Board.State = STATE.GAME;
		}
	}

	// Új játék kezdésekor mit csináljon
	public static void newGame() {
		Bolygo.initplanet();
//		PlanetInterface.buildingBool=false;
		Hajo.newShip();
		Bolygo.checkplanet();
		Board.stopmovement();
		Bolygo.checkplanet();
		Board.spawn();
		Board.State = STATE.GAME;
	}

}
