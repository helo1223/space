//Mindenféle dologért felel, mert lusta vagyok rendezni
//néha angol néha magyar neveket használok a kedvemtõl függõen, ez rossz, ne csináld :(

package com.Helo.SpaceGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener, MouseMotionListener, MouseWheelListener {
	// Adatszerkezet felépítése
	int WORLD_SIZE_X = 8000;
	int WORLD_SIZE_Y = 6000;
	int VIEWPORT_SIZE_X = Ablak.screenW;
	int VIEWPORT_SIZE_Y = Ablak.screenH;
	private final int DELAY = 10;

	private Timer timer;
	public static Image myship, landbutton, colonizebutton, attackbutton;
	public static Image coinicon;

	int offsetMaxX = WORLD_SIZE_X - VIEWPORT_SIZE_X;
	int offsetMaxY = WORLD_SIZE_Y - VIEWPORT_SIZE_Y;
	int offsetMinX = 0;
	int offsetMinY = 0;
	int camX = playerX - VIEWPORT_SIZE_X / 2;
	int camY = playerY - VIEWPORT_SIZE_Y / 2;
	private double zoom = 1d;

	static int playerX, playerY;

	// String pos;
	// int Mx, My;
	private static Point mousePoint;

	static boolean valami = false;
	int x1, x2, y1, y2;

	static boolean inRange = false;
	public static Rectangle landbuttonRect;

	static boolean saveexists;

	private Background background;
	private Stars stars;
	private Fuel fuel;
	private Bolygo bolygo;
	private Menu menu;
	private Pause pause;
	private PlanetInterface planetinterface;
	private ErrorMessage errormessage;

	static boolean displayerror = false;

	public static long currtime;

	public static double angle;
	public static int differenceX, differenceY;
	
	// Különbözõ játékállások deklarálása, mint pl Menü
	public enum STATE {
		MENU, GAME, PAUSE, PLANET
	};

	// Menüvel induljon a program
	public static STATE State = STATE.MENU;

	public Board() {

		initBoard();
		SaveSys.checkFile(); // Fájlok létezését ellenõrzõ metódus
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // Ûrhajó mozgása idõzítve
				if (mousePoint != null && Hajo.fuel > 0 && State == STATE.GAME) {
					int centerX = playerX + (50 / 2);
					int centerY = playerY + (50 / 2);
				    differenceX = mousePoint.x - centerX;
				    differenceY = mousePoint.y - centerY;
				    angle = (float)Math.atan2(differenceY, differenceX) * 180 / Math.PI;

					if (mousePoint.x != centerX) {
//						playerX += mousePoint.x < centerX ? -1.2 : 1.2;
						playerX += Math.cos(angle * Math.PI/180) * 2;

					}
					if (mousePoint.y != centerY) {
//						playerY += mousePoint.y < centerY ? -1.2 : 1.2;
						playerY += Math.sin(angle * Math.PI/180) * 2;

					}
					if (mousePoint.y == centerY && mousePoint.x == centerX) {
						valami = false;
					}
					if (Hajo.fuel <= 0) {
						valami = false;
						Hajo.fuel = 0;
					}
					if (Hajo.fuel == 0) {
						valami = false;
						Hajo.fuel = 0;
					}
					if (State == STATE.GAME) {
						checkInRange();
					}

					repaint();
				}
			}
		});
		timer.start();
	}

	private void initBoard() {
		// Mindenféle listener inicializálása
		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		addMouseListener(new MouseInput());
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		loadImage(); // képeket betöltõ metódus
		timer = new Timer(DELAY, this);
		timer.start();
		background = new Background();
		stars = new Stars();
		fuel = new Fuel();
		bolygo = new Bolygo();
		menu = new Menu();
		pause = new Pause();
		planetinterface = new PlanetInterface();
		errormessage = new ErrorMessage();

	}

	// Játékos adatainak betöltése fájlból
	public static void loadPlayer() {
		String b64;
		String loaded;
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader("player.save");
			br = new BufferedReader(fr);
			b64 = br.readLine();
			loaded = new String(DatatypeConverter.parseBase64Binary(b64));
			playerX = Integer.parseInt(loaded);
			b64 = br.readLine();
			loaded = new String(DatatypeConverter.parseBase64Binary(b64));
			playerY = Integer.parseInt(loaded);
			saveexists = true;
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			saveexists = false;
			System.err.println("Játékos mentés nem található");
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Új játék esetén véletlenszerû barátságos bolygón kezdjen a játékos
	public static void spawn() {
		int rand;
		do {
			rand = (int) (Math.random() * 30);
			playerX = Integer.parseInt(Bolygo.plansplit[rand][3]);
			playerY = Integer.parseInt(Bolygo.plansplit[rand][4]);
			// mousePoint.setLocation(playerX, playerY);
		} while (Integer.parseInt(Bolygo.plansplit[rand][2]) != 1);
	}

	public static void stopmovement() {
		valami = false;
		mousePoint = null;
	}

	// Képek betöltése a resource mappából
	private void loadImage() {

		URL myshipU = Board.class.getResource("/resource/spaceship1_1.png");
		ImageIcon myshipI = new ImageIcon(myshipU);
		myship = myshipI.getImage();

		URL landbuttonU = Board.class.getResource("/resource/button_land.png");
		ImageIcon landbuttonI = new ImageIcon(landbuttonU);
		landbutton = landbuttonI.getImage();

		URL colonizebuttonU = Board.class.getResource("/resource/button_colonize.png");
		ImageIcon colonizebuttonI = new ImageIcon(colonizebuttonU);
		colonizebutton = colonizebuttonI.getImage();

		URL attackbuttonU = Board.class.getResource("/resource/button_attack.png");
		ImageIcon attackbuttonI = new ImageIcon(attackbuttonU);
		attackbutton = attackbuttonI.getImage();
		
		URL coinU = Board.class.getResource("/resource/coin2.png");
		ImageIcon coinI = new ImageIcon(coinU);
		coinicon = coinI.getImage();	
		}

	@Override

	// Fõ rajzoló metódus
	public void paintComponent(Graphics g) {
		landbuttonRect = new Rectangle((Ablak.screenW / 2) - 102, Ablak.screenH - 83, 204, 54);
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if (State == STATE.GAME) {
			double width = getWidth();
			double height = getHeight();

			double zoomWidth = width * zoom;
			double zoomHeight = height * zoom;

			double anchorx = (width - zoomWidth) / 2;
			double anchory = (height - zoomHeight) / 2;
			AffineTransform at = new AffineTransform();
			at.translate(anchorx, anchory);
			// Zoom
			at.scale(zoom, zoom);
			// Játékost követõ kamera
			at.translate(-camX, -camY);

			// debugDrawing(g2d);
			g2d.setTransform(at);
			background.paint(g2d);
			stars.draw(g2d);
			bolygo.draw(g2d);
			doDrawing(g2d);
			drawBar(g);

			if (inRange == true) { // Ha a játékos elég közel van a bolygóhoz, jelenítse meg a
									// leszállás/gyarmatosítás/támadás gombokat
				uiDrawing(g);
			}
			fuel.paint(g); // Üzemanyagállás megjelenítése

			Toolkit.getDefaultToolkit().sync();
		} else if (State == STATE.MENU) { // Ha a játékállás MENU, akkor a menüt jelenítse meg
			menu.render(g2d);
		} else if (State == STATE.PAUSE) {
			pause.render(g2d);
		} else if (State == STATE.PLANET) {
			planetinterface.render(g2d);
			int x=ErrorMessage.x;
			if (displayerror==true) {
				switch(x) {
				case 1: errormessage.paint(g,1);break;
				case 2: errormessage.paint(g,2);break;
				default: errormessage.paint(g,0);break;

			
				}
				}
		}


	}

	// Játékost megjelenítõ rajzoló metódus
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int cx = 50 / 2;
		int cy = 50 / 2;
		AffineTransform oldAT = g2d.getTransform();
		g2d.translate(cx + playerX, cy + playerY);
		g2d.translate(-cx, -cy);
		g2d.drawImage(myship, 0, 0, null);
		g2d.setTransform(oldAT);
		if (valami == true) {
			g2d.setColor(Color.WHITE);
			g2d.drawLine(playerX + cx, playerY + cy, x2, y2); // Ha kiadtuk a mozgásparancsot, húzzon vonalat a
																// játékostól a célig
			

		}

	}
	
	

	// Egérkoordináta megjelenítése a képernyõn
	// private void debugDrawing(Graphics g) {
	// g.setColor(Color.RED);
	// g.drawString(pos, 500, 400);
	//
	// }

	// Valami, nem túl leíró
	// amúgy vizsgálja, hogy van-e kiadva mozgásparancs
	public static boolean getValami() {
		return valami;
	}

	// Leszállás/gyarmatosítás/támadás gombok megjelenítése attól függõen, hogy
	// milyen csoportú bolygón vagyunk
	private void uiDrawing(Graphics g) {
		if (Bolygo.plansplit[onID][2].equals("1")) {
			g.drawImage(landbutton, (Ablak.screenW / 2) - 102, Ablak.screenH - 83, this);
		} else if (Bolygo.plansplit[onID][2].equals("2")) {
			g.drawImage(attackbutton, (Ablak.screenW / 2) - 102, Ablak.screenH - 83, this);
		} else if (Bolygo.plansplit[onID][2].equals("0")) {
			g.drawImage(colonizebutton, (Ablak.screenW / 2) - 102, Ablak.screenH - 83, this);
		}

	}
	
	private void drawBar(Graphics g) {
		
		g.setColor(Color.GRAY);
		g.fillRect(0,0,800,20);
		g.drawImage(coinicon, 680, 0, 20,20, this);
		g.setColor(Color.yellow);
		g.drawString(Double.toString(Hajo.gold), 710, 15);
	}

	static int onID;

	// Jelenlegi bolygó azonosítójának lekérdezése
	// lehet hogy nem a legjobb megoldás de mûködik
	// összehasonlítja az összes bolygó koordinátáit a játékoséval és az alapján
	// határozza meg az azonosítót
	public static void getPlanetID() {
		for (int i = 0; i < 30; i++) {
			if ((playerX <= Integer.parseInt(Bolygo.plansplit[i][3]) + 50
					&& playerX >= Integer.parseInt(Bolygo.plansplit[i][3]) - 25)
					&& (playerY <= Integer.parseInt(Bolygo.plansplit[i][4]) + 50
							&& playerY >= Integer.parseInt(Bolygo.plansplit[i][4]) - 25)) {
				// System.out.println(Integer.parseInt(Bolygo.plansplit[i][0]));
				// System.out.println(Bolygo.plansplit[i][1]);
				onID = Integer.parseInt(Bolygo.plansplit[i][0]);
				// System.out.println(Bolygo.plansplit[i][2]);
			}
		}
	}

	@Override
	// Akármilyen esemény történésekor lefut a metódus
	public void actionPerformed(ActionEvent e) {
		currtime = System.currentTimeMillis();
		Hajo.checkMoving(); // Ellenõrzi, hogy a játékos mozog-e
		if (State == STATE.GAME) {
			checkInRange(); // Ha játékon belül van, ellenõrzi, hogy bolygó közelében van-e

		}
		repaint(); // Újrarajzol mindent

		// Kamera mozgatása
		camX = playerX - 800 / 2;
		camY = playerY - 600 / 2;
		if (camX > offsetMaxX) {
			camX = offsetMaxX;
		} else if (camX < offsetMinX) {
			camX = offsetMinX;
		}
		if (camY > offsetMaxY) {
			camY = offsetMaxY;
		} else if (camY < offsetMinY) {
			camY = offsetMinY;
		}

		if (playerX > offsetMaxX) {
			playerX = offsetMaxX;
		} else if (playerX < offsetMinX) {
			playerX = offsetMinX;
		}
		if (playerY > offsetMaxY) {
			playerY = offsetMaxY;
		} else if (playerY < offsetMinY) {
			playerY = offsetMinY;
		}
	}

	// Billentyûzetre figyel
	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				if (State == STATE.GAME) { // Játékon belül SPACE gombra alapállásra állítja a zoom-ot
					zoom = 1d;
				}
				if (State == STATE.PLANET) {
					
				}

			}

			if (key == KeyEvent.VK_ESCAPE) { // Pause menü, és abból kilépés
				if (State == STATE.GAME) {
					State = STATE.PAUSE;
				} else if (State == STATE.PAUSE || State == STATE.PLANET) {
					State = STATE.GAME;
				}
			}
		}
	}

	// Ellenõrzi, hogy a játékos bolygó közelében van-e
	// Itt is a koordináták alapján ellenõriz, mint korábban
	public void checkInRange() {
		int i = 0;
		do {
			if ((playerX <= Integer.parseInt(Bolygo.plansplit[i][3]) + 50
					&& playerX >= Integer.parseInt(Bolygo.plansplit[i][3]) - 25)
					&& (playerY <= Integer.parseInt(Bolygo.plansplit[i][4]) + 50
							&& playerY >= Integer.parseInt(Bolygo.plansplit[i][4]) - 25)) {
				inRange = true;
				getPlanetID();
			} else {
				inRange = false;
				i++;
			}
		} while (inRange != true && i < 30);
		i = 0;

		// Ha elég közel kattintunk a bolygóra, automatikusan ugorjon a cél a bolygó
		// közepére
		// try {
		for (int j = 0; j < 30; j++) {
			if ((x2 <= Integer.parseInt(Bolygo.plansplit[j][3]) + 50
					&& x2 >= Integer.parseInt(Bolygo.plansplit[j][3]) - 25)
					&& (y2 <= Integer.parseInt(Bolygo.plansplit[j][4]) + 50
							&& y2 >= Integer.parseInt(Bolygo.plansplit[j][4]) - 25)) {
				x2 = Integer.parseInt(Bolygo.plansplit[j][3]) + 25;
				y2 = Integer.parseInt(Bolygo.plansplit[j][4]) + 25;
				if (mousePoint != null) {
					mousePoint.setLocation(x2, y2);
				}
			}
		}
		// }catch (Exception e) {

		// }
	}

	// Egérre figyel
	private class MAdapter extends MouseAdapter {
		@Override
		// Mi történik, ha kattintunk az egérrel
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e) && State == STATE.GAME) { // Figyelje, hogy jobb-kattintás legyen,
																				// és játékon belül
				mousePoint = e.getPoint(); // Lekérdezi az egér pozicióját, és eltárolja Point-ként
				mousePoint.translate(camX, camY); // A kamera állását figyelembe veszi

				// Vonalhúzáshoz szükséges koordináták
				x1 = e.getX();
				y1 = e.getY();
			}

			repaint();

			if (landbuttonRect.contains(e.getPoint()) && inRange == true && State == STATE.GAME) { // Ha a
																									// leszállás/gyarmatosítás/támadás
																									// gombra
																									// kattintunk,
																									// lépján át
																									// bolygófelület-re
				System.out.println("hello"); // hello neked is
				State = STATE.PLANET;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		// Mi történik, ha elengedtük az egérgombot, majdnem ugyanaz mint a kattintás
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e) && State == STATE.GAME) { // Jobb-klikk ellenõrzés
				mousePoint = e.getPoint();
				mousePoint.translate(camX, camY);
				x2 = mousePoint.x;
				y2 = mousePoint.y;
				for (int j = 0; j < 30; j++) {
					if ((x2 <= Integer.parseInt(Bolygo.plansplit[j][3]) + 50
							&& x2 >= Integer.parseInt(Bolygo.plansplit[j][3]) - 25)
							&& (y2 <= Integer.parseInt(Bolygo.plansplit[j][4]) + 50
									&& y2 >= Integer.parseInt(Bolygo.plansplit[j][4]) - 25)) {
						x2 = Integer.parseInt(Bolygo.plansplit[j][3]) + 25;
						y2 = Integer.parseInt(Bolygo.plansplit[j][4]) + 25;
						mousePoint.setLocation(x2, y2);

					}

					// Üzemanyag ellenõrzése, ha nincs üzemanyag, nincs mozgás
					if (Hajo.fuel > 0) {
						valami = true;
					}
					if (Hajo.fuel == 0) {
						valami = false;
					}
				}
			}

		}

	}

	// Mi történik, ha nyomva tartva mozgatjuk az egeret
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e) && State == STATE.GAME) { // Jobb-klikk még mindíg
			// Real-time egér követés a célig húzott vonalhoz :O
			valami = true;
			mousePoint = e.getPoint();
			mousePoint.translate(camX, camY);
			x2 = mousePoint.x;
			y2 = mousePoint.y;
		}

	}

	// Mi történik, ha mozog az egér (debug-hoz kellett, mostmár semmi)
	public void mouseMoved(MouseEvent e) {
		// Mx = e.getX();
		// My = e.getY();
		// pos = Integer.toString(Mx) + "x" + Integer.toString(My) + "y";
		// repaint();
	}

	// Mi történik, ha tekerjük a görgõt
	// zoom, ennyi
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getPreciseWheelRotation() < 0 && State == STATE.GAME) {
			zoom -= 0.1;
		} else {
			if (State == STATE.GAME)
				zoom += 0.1;
		}
		//if (zoom < 0.50) {
			//zoom = 0.50;

		//}
	//	if (zoom > 5) {
		//	zoom = 5;
		//}
		repaint();

	}

}