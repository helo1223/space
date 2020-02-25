package com.Helo.SpaceGame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.Timer;




@SuppressWarnings("serial")
public class ErrorMessage extends JPanel{
	
	static float alpha = 0.0f;
	public static Thread t1;
	public static boolean lefutott=false;
	public Timer timer1;
	public Timer timer2;
	public static boolean vis=false;
	public static int x;
	public ErrorMessage() {
	}

	public  void paint(Graphics g, int code) {

		
	    Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Verdana", Font.BOLD, 18);
		Color co = new Color(200,20,20);
	    g2d.setColor(co);
	    g2d.setFont(font);
   	    g2d.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, alpha));
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    String message;
	    
	    switch(code) {
	    case 1:message="NINCS ELÉG PÉNZED";break;
	    case 2:message="MÁR ÉPÍTESZ VALAMIT";break;
	    default:message="ERROR_NO_MESSAGE";break;
	    }
	    
	    g2d.drawString(message, 100, 500);
	    if (lefutott==false) {
	    fadein();
	    }else {
	    fadeout();
	    }
	    

	}
	
	public void fadein() {
			    alpha += 0.01f;
			    if (alpha >= 1) {
			      alpha = 1;
			      lefutott=true;
			      
			    }
			    repaint();
			    
			  }
	
	
	public void fadeout() {
			    alpha += -0.01f;
			    if (alpha <= 0) {
			      alpha = 0;
//			      lefutott=false;
			    }
			    repaint();
	}

	public void runError(int code) {
		Board.displayerror=true;
		ErrorMessage.lefutott=false;
		x=code;
	}
	
	}