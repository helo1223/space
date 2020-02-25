//Ablak megjelenítésért felel
//ablakméret, stb. beállítható,
//gombok koordinátájához a képernyõ szélességét & magasságát használja a program

package com.Helo.SpaceGame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.Helo.SpaceGame.Board.STATE;

@SuppressWarnings({ "serial", "unused" })
public class Ablak extends JFrame {

	public static int screenW;
	public static int screenH;
	public static boolean fscreen = false;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Ablak() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		setSize(800, 600);
		setResizable(false);
		setTitle("SpaceGame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(0,0,screenSize.width, screenSize.height);

		screenW = this.getWidth();
		screenH = this.getHeight();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Ablak ex = new Ablak();
				ex.setVisible(true);

			}
		});
	}
}