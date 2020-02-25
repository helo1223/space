//Egér inputért felelõs

package com.Helo.SpaceGame;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.Helo.SpaceGame.Board.STATE;

public class MouseInput implements MouseListener {

	@Override
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		Point mP = new Point(mx, my); // egérkoordináták letárolása
		if (Board.State == STATE.MENU) { // Ha menüben vagyunk, a menügombokat figyelje
			if (Menu.newButton.contains(mP)) {
				SaveSys.newGame();
			}
			if (Menu.quitButton.contains(mP)) {
				System.exit(1);
			}
			if (Menu.loadButton.contains(mP)) {
				SaveSys.load();
			}
		}

		if (Board.State == STATE.PAUSE) {
			if (Pause.continueButton.contains(mP)) {
				Board.State = STATE.GAME;
			}
			if (Pause.saveButton.contains(mP)) {
				SaveSys.Save();
				Board.State = STATE.GAME;
			}
			if (Pause.loadButton.contains(mP)) {
				SaveSys.load();
			}
			if (Pause.quitButton.contains(mP)) {
				Board.State = STATE.MENU;
			}
		}
		
		if (Board.State == STATE.PLANET){
			if(Bolygo.plansplit[Board.onID][2].equals("1")) {
			if (PlanetInterface.upgradeBase.contains(mP)){
				if (Hajo.gold<100) {
					ErrorMessage.x=1;
					Board.displayerror=true;
					ErrorMessage.lefutott=false;
					}else if (PlanetInterface.buildingBool[PlanetInterface.currentID]==true /*&& Board.onID==PlanetInterface.currentID*/){
						ErrorMessage.x=2;
						Board.displayerror=true;
						ErrorMessage.lefutott=false;
					}else {
						PlanetInterface.setID();
						PlanetInterface.startBuilding(1);
					}
				
			}
			if (PlanetInterface.upgradeMine.contains(mP)){
				if (Hajo.gold<100) {
					ErrorMessage.x=1;
					Board.displayerror=true;
					ErrorMessage.lefutott=false;
					}else if (PlanetInterface.buildingBool[PlanetInterface.currentID]==true){
						ErrorMessage.x=2;
						Board.displayerror=true;
						ErrorMessage.lefutott=false;
					}else {
						PlanetInterface.startBuilding(2);
					}
			}
			if (PlanetInterface.upgradeBarracks.contains(mP)){
				if (Hajo.gold<100) {
					ErrorMessage.x=1;
					Board.displayerror=true;
					ErrorMessage.lefutott=false;
					}else if (PlanetInterface.buildingBool[PlanetInterface.currentID]==true){
						ErrorMessage.x=2;
						Board.displayerror=true;
						ErrorMessage.lefutott=false;
					}else {
						PlanetInterface.startBuilding(3);
					}
			}
			if (PlanetInterface.upgradeWall.contains(mP)){
				if (Hajo.gold<100) {
					ErrorMessage.x=1;
					Board.displayerror=true;
					ErrorMessage.lefutott=false;
					}else if (PlanetInterface.buildingBool[PlanetInterface.currentID]==true){
						ErrorMessage.x=2;
						Board.displayerror=true;
						ErrorMessage.lefutott=false;
					}else {
						PlanetInterface.startBuilding(4);
					}
			}
			}
			if (PlanetInterface.colonizeRect.contains(mP)) {
				System.out.println("kkkkolonizál");
				PlanetInterface.colonize();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
