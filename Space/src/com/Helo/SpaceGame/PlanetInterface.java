//Bolygófelület megjelenítéséért felelõs
//Hát ja, még nem csinál sok mindent
//ID | NAME | FACTION | X | Y | MINERAL | BASELVL | MINELVL | BARRACKSLVL | WALLSLVL

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.Helo.SpaceGame.Board.STATE;





@SuppressWarnings("serial")
public class PlanetInterface extends JPanel implements ActionListener{

	
	public static Image mineral, upgradeButton, colonizeButton;
	public static Rectangle upgradeBase;
	public static Rectangle upgradeMine;
	public static Rectangle upgradeBarracks;
	public static Rectangle upgradeWall;
	public static Rectangle colonizeRect;
	public static long lastClick;
	public static Thread t1[] = new Thread[30];
	static int x1,x2,x3,x4;
	public static boolean buildingBool[] = new boolean[30];
	public ErrorMessage errormessage;
	public static boolean errorvis=false;
	public static int currentID;
	static int startID;
	static int constq=0;
	static int ids[]=new int[30];
	static int colonizeCharge=0;
	private static Timer timer;
		
	public PlanetInterface() {
		for (int i = 0; i < buildingBool.length; i++) {
			buildingBool[i]=false;
		}
		
		errormessage = new ErrorMessage();
		Timer timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // Ûrhajó mozgása idõzítve
				if(buildingBool[currentID]==true) {
				remain=""+(((finishTime-Board.currtime)/1000)+1);
		    	System.out.println("building currentID "+currentID);

				}
					repaint();
			}
			
		});
		timer.start();

		URL mineralU = Board.class.getResource("/resource/mineral.png");
		ImageIcon mineralI = new ImageIcon(mineralU);
		mineral = mineralI.getImage();
		
		URL upbuttonU = Board.class.getResource("/resource/button_upgrade.png");
		ImageIcon upbuttonI = new ImageIcon(upbuttonU);
		upgradeButton = upbuttonI.getImage();
     
		URL colonizebuttonU = Board.class.getResource("/resource/button_colonize.png");
		ImageIcon colonizebuttonI = new ImageIcon(colonizebuttonU);
		colonizeButton = colonizebuttonI.getImage();
		

	}


	public void render(Graphics g) {
		   super.paintComponent(g);
		   Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 2000, 2000);
		g.setColor(Color.BLACK);
		
		if(Bolygo.plansplit[Board.onID][2].equals("1")) {
		g.drawRect(50, 50, 300, 100);
		g.drawRect(50, 150, 300, 100);
		g.drawRect(50, 250, 300, 100);
		g.drawRect(50, 350, 300, 100);
		}
		g.drawImage(Bolygo.planet, 500, 100, null);
		g.drawImage(mineral, 450, 300, 50, 50, null);
		g.setColor(Color.CYAN);
		g.drawString(Bolygo.plansplit[Board.onID][5], 500, 340);

		if (Bolygo.plansplit[Board.onID][2].equals("1")) {
			g.setColor(Color.green);
		}
		if (Bolygo.plansplit[Board.onID][2].equals("2")) {
			g.setColor(Color.red);
		}
		if (Bolygo.plansplit[Board.onID][2].equals("0")) {
			g.setColor(Color.white);
		}
		
		Font font = new Font("Verdana", Font.BOLD, 12);
	    g.setFont(font);
	    g.drawString(Bolygo.plansplit[Board.onID][1], 525, 85);
	    
	    if(Bolygo.plansplit[Board.onID][2].equals("1")) {
	    g.drawString("Lvl. "+Bolygo.plansplit[Board.onID][6], 300, 140);
	    g.drawString(""+price(6), 250, 140);
	    g.drawImage(Board.coinicon, 220, 125, 20, 20, this);
	    g.drawString("Lvl. "+Bolygo.plansplit[Board.onID][7], 300, 240);
	    g.drawString(""+price(7), 250, 240);
	    g.drawImage(Board.coinicon, 220, 225, 20, 20, this);
	    g.drawString("Lvl. "+Bolygo.plansplit[Board.onID][8], 300, 340);
	    g.drawString(""+price(8), 250, 340);
	    g.drawImage(Board.coinicon, 220, 325, 20, 20, this);
	    g.drawString("Lvl. "+Bolygo.plansplit[Board.onID][9], 300, 440);
	    g.drawString(""+price(9), 250, 440);
	    g.drawImage(Board.coinicon, 220, 425, 20, 20, this);

	    g.drawString("Fõbázis", 55, 70);
	    g.drawString("Bánya", 55, 170);
	    g.drawString("Barakk", 55, 270);
	    g.drawString("Védõfal", 55, 370);
	    }
	    if(Bolygo.plansplit[Board.onID][2].equals("1")){
	    upgradeBase = new Rectangle(55,110,120,30);
	    g.drawImage(upgradeButton, 55,117,120,30,null);
	    upgradeMine = new Rectangle(55,220,120,30);
	    g.drawImage(upgradeButton, 55,217, 120,30,null);
	    upgradeBarracks = new Rectangle(55,320,120,30);
	    g.drawImage(upgradeButton, 55,317, 120,30,null);
	    upgradeWall = new Rectangle(55,420,120,30);
	    g.drawImage(upgradeButton, 55,417, 120,30,null);
	    }
	    if(buildingBool[currentID]==true && Board.onID==currentID) {
	    g.drawString(remain, 55, x1);
	    }
	    
	    if(Bolygo.plansplit[Board.onID][2].equals("0")) {
	    	colonizeRect = new Rectangle(100, 100, 204, 54);
	    	g.drawImage(colonizeButton, 100, 100, null);
		    g.setColor(Color.BLACK);
	    	g.drawRect(100, 160, 204, 27);
	    	g.setColor(Color.GREEN);
	    	g.fillRect(101, 161, colonizeCharge, 26);
	    }
	    
	    repaint();
	    

	    

	}
	
	static long startTime;
	static long finishTime;
	
	public static void setID() {
		currentID=Board.onID;
		System.out.println("currentID "+currentID);
	}
	
	
	public static void startBuilding(int building) {
		setID();
		startID=currentID;
        t1[currentID] = new Thread(new Runnable() {
            @SuppressWarnings("static-access")
			public void run() {
            	System.out.println(t1[currentID]);
            	startTime=System.currentTimeMillis();
            	int x=0;
            	switch(building) {
            	case 1:x=6;x1=100;break;
            	case 2:x=7;x1=200;break;
            	case 3:x=8;x1=300;break;
            	case 4:x=9;x1=400;break;
            	}
//            	System.out.println(Bolygo.plansplit[currentID][x]);
            	int time=10000*(Integer.valueOf((Bolygo.plansplit[currentID][x])));
            	if (Integer.valueOf(Bolygo.plansplit[currentID][x])==0) {
            		time=5000;
            	}
            	finishTime=startTime+time;
        		buildingBool[currentID]=true;
            	try {
					t1[currentID].sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

            	if(buildingBool[currentID]==true) {
            	switch(building){
            	case 1:upgradeBase();break;
            	case 2:upgradeMine();break;
            	case 3:upgradeBarracks();break;
            	case 4:upgradeWall();break;
            	}
            	}
//            	System.out.println(building);
                buildingBool[currentID]=false;

            }

       });  
	       t1[currentID].start();
	       

            }
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	public static void upgradeBase(){
	    if(Bolygo.plansplit[currentID][2].equals("1")){
	    int lvl=Integer.parseInt(Bolygo.plansplit[currentID][6]);
		Hajo.gold-=price(6);
	    if (lvl<10)
	    lvl++;
	    Bolygo.plansplit[currentID][6]=Integer.toString(lvl);
	    }
	}


	
	public static void upgradeMine(){
	    if(Bolygo.plansplit[currentID][2].equals("1")){
		int lvl=Integer.parseInt(Bolygo.plansplit[currentID][7]);
		Hajo.gold-=price(7);
		if (lvl<10)
		lvl++;
		Bolygo.plansplit[currentID][7]=Integer.toString(lvl);
	    }
		
	}
	
	static String remain="";

	
	public static void upgradeBarracks(){
	    if(Bolygo.plansplit[currentID][2].equals("1")){
		int lvl=Integer.parseInt(Bolygo.plansplit[currentID][8]);
		Hajo.gold-=price(8);
		if (lvl<10)
		lvl++;
		Bolygo.plansplit[currentID][8]=Integer.toString(lvl);
	    }
	}
	
	public static void upgradeWall(){
	    if(Bolygo.plansplit[currentID][2].equals("1")){
		int lvl=Integer.parseInt(Bolygo.plansplit[currentID][9]);
		Hajo.gold-=price(9);
		if (lvl<10)
		lvl++;
		Bolygo.plansplit[currentID][9]=Integer.toString(lvl);
	    }
	}
	static int cost;
	public static int price(int building) {
		int x=0;
		switch(building){
			case 6:x=6;break;
			case 7:x=7;break;
			case 8:x=8;break;
			case 9:x=9;break;
			default : System.out.println("error");break;
			
		}
		if (Integer.parseInt(Bolygo.plansplit[currentID][x])==0) {
			cost=50;
		}else {
			cost=100*(Integer.parseInt(Bolygo.plansplit[currentID][x]));
		}
		return cost;
	}

	public static void colonize() {
		timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // Ûrhajó mozgása idõzítve
				if(colonizeCharge<203) {
					colonizeCharge++;
					System.out.println(colonizeCharge);
				}
				
				if(colonizeCharge==203) {
					colonizeFinish();
					timer.stop();
					colonizeCharge=0;
					System.out.println("hmm");
				}
				
			}
		});

		timer.start();

	}
	
	public static void colonizeFinish() {
		Bolygo.plansplit[Board.onID][2]="1";
	}

}
