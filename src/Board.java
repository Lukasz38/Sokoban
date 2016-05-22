import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Klasa reprezentuj¹ca planszê
 * Odpowiedzialna za narysowanie planszy oraz ruch
 *
 */
public class Board extends JPanel implements KeyListener
{
	
	/** wysokoœæ pojedynczego klocka planszy*/
	private final int HEIGHT = 50;
	/** szerokoœæ klocka planszy */
	private final int WIDTH = 50;	
	/** pole gdy nie ma planszy */
	private final int EMPTY = 0;
	/** pole, które jest œcian¹ */
	private final int WALL = 1;
	/** pole, które jest pod³og¹ */
	private final int FLOOR = 2;
	/** pole, które jest paczk¹ */
	private final int BOX = 3;
	/** pole, które jest docelowym miejscem dla paczki  */
	private final int DESTINATION  = 4;
	/** pole, na którym jest postaæ */
	private final int MAN = 5;
	/** pole, które jest bonusem */
	private final int BONUS = 6;
	/** pole, gdy postaæ stoi na klocku docelowym */
	private final int MANONDEST = 7;
	/** pole, gdy paczka stoi na klocku docelowym */
	private final int BOXONDEST = 8;
	/**
	 * Pozycja horyzontana
	 */
	private int x;
	/**
	 * Pozycja wertykalna
	 */
	private int y;

	/**
	 * Tablica opisuj¹ca wygl¹d poziomu
	 */
	private int [][] levelBoard;
	/** Liczba paczek nieustawionych na miejscu docelowym */
	private int numberOfFreeBoxes = 0;
	/**
	 * Obiekty klasy Image, zachowuj¹ wczytane obrazy z plików
	 */
	private Image wallImage, floorImage, boxImage, destinationImage, manImage, bonusImage;
	/**
	 * Konstruktor z parametrem
	 * Tworzy obiekt klasy w zale¿noœci od podanego poziomu gry
	 * @param levelNumber numer poziomu gry/id poziomu
	 */
	public Board(int levelNumber, ConfigReader con)
	{
		loadImages(con);
		levelBoard = con.lr.levels.get(levelNumber).getMap();
		countFreeBoxes();
		addKeyListener(this);
		//this.requestFocus();
		
	}
	//-------------------------------------------------------------------------------------------------------
	
	/**
	 * Funkcja odpowiedzialna za rysowanie planszy
	 * 
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		createBoard(g);
	}
	//-------------------------------------------------------------------------------------------------------
	
	/**
	 * Funkcja pomocnicza, rysuje poziom gry
	 * @param g kontekst graficzny
	 */
	public void createBoard(Graphics g)
	{
		//g.setBackground(Color.WHITE);
		
		for (int i = 0; i < levelBoard.length; i++)
		{
			for(int j= 0; j<levelBoard[i].length; j++)
			{
				
				switch(levelBoard[i][j])
				{
				case EMPTY:
					g.drawRect( WIDTH*j, HEIGHT*i, WIDTH, HEIGHT);
					break;
				case WALL:
					g.drawImage(wallImage,  WIDTH*j, HEIGHT*i, null );
					break;
				case FLOOR:
					g.drawImage(floorImage,  WIDTH*j, HEIGHT*i, null );
					break;
				case BOX:
					g.drawImage(boxImage, WIDTH*j, HEIGHT*i, null );
					break;
				case DESTINATION:
					g.drawImage(destinationImage,WIDTH*j, HEIGHT*i, null );
					break;
				case MAN:
					x = j;
					y = i;
					g.drawImage(floorImage, WIDTH*j, HEIGHT*i, null );
					//g.drawImage(manImage, WIDTH*j, HEIGHT*i, null );
					g.setColor(Color.WHITE);
					g.drawOval(WIDTH*j, HEIGHT*i, WIDTH, HEIGHT);
					g.fillOval(WIDTH*j, HEIGHT*i, WIDTH, HEIGHT);
					break;
				case MANONDEST:
					x = j;
					y = i;
					g.drawImage(destinationImage,WIDTH*j, HEIGHT*i, null );
					//g.drawImage(manImage, WIDTH*j, HEIGHT*i, null );
					g.setColor(Color.WHITE);
					g.drawOval(WIDTH*j, HEIGHT*i, WIDTH, HEIGHT);
					g.fillOval(WIDTH*j, HEIGHT*i, WIDTH, HEIGHT);
					
					break;
				case BOXONDEST:
					g.drawImage(boxImage, WIDTH*j, HEIGHT*i, null );
					break;
				}
			}
		}
	}
	//-------------------------------------------------------------------------------------------------------
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			moveUp();
			//move('u');
			break;
		case KeyEvent.VK_DOWN:
			moveDown();
			break;
		case KeyEvent.VK_LEFT:
			moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			moveRight();
			break;
		default:
			break;
		}
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda pomocnicza zmieniaj¹ca pozycjê w zale¿noœci od kierunku ruchu
	 * 
	 * @param x1 kierunek ruchu w poziomie w zale¿noœci: x-1(lewo)/x+1(prawo)
	 * @param y1 kierunek ruchu w pionie w zale¿noœci: y-1(gora)/y+1(dol)
	 */
	private void positionChange(int x1, int y1)
	{
		if(x1 == (x-1))
			x--;
		else if(x1 == (x+1))
			x++;
		else if(y1 == (y-1))
			y--;
		else if(y1 == (y+1))
			y++;
	}
//===============================================================================
	/** Metoda pomocnicza steruj¹ca ruchem postaci 
	 * 
	 * @param y0 aktualna pozycja w pionie
	 * @param y1 pozycja docelowa odlegla o 1 pole w pionie w zale¿noœci: y-1(gora)/y+1(dol)
	 * @param y2 pozycja docelowa odlegla o 2 pola w pionie w zale¿noœci: y-2(gora)/y+2(dol)
	 * @param x0 aktualna pozycja w poziomie
	 * @param x1 pozycja docelowa odlegla o 1 pole w poziomie w zale¿noœci: x-1(lewo)/x+1(prawo)
	 * @param x2 pozycja docelowa odlegla o 2 pola w poziomie w zale¿noœci: x-2(lewo)/x+2(prawo)
	 */
	private void move(int x0, int x1, int x2, int y0, int y1, int y2)
	{
		
		if(levelBoard[y1][x1] == WALL);
		else if((levelBoard[y0][x0] == MAN) && (levelBoard[y1][x1] == FLOOR))
		{
			levelBoard[y1][x1] = MAN;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
		}
		else if((levelBoard[y0][x0] == MANONDEST) && (levelBoard[y1][x1] == DESTINATION))
		{
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y0][x0] = DESTINATION;
			positionChange(x1, y1);
		}
		else if((levelBoard[y0][x0] == MANONDEST) && (levelBoard[y1][x1] == FLOOR))
		{
			levelBoard[y1][x1] = MAN;
			levelBoard[y0][x0] = DESTINATION;
			positionChange(x1, y1);
		}
		else if((levelBoard[y1][x1] == BOX) && (levelBoard[y2][x2] == FLOOR))
		{
			levelBoard[y2][x2] = BOX;
			levelBoard[y1][x1] = MAN;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
		}
		else if((levelBoard[y1][x1] == BOX) && (levelBoard[y2][x2] == DESTINATION))
		{
			levelBoard[y2][x2] = BOXONDEST;
			levelBoard[y1][x1] = MAN;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
			numberOfFreeBoxes--;
		}
		else if((levelBoard[y1][x1] == BOXONDEST) && (levelBoard[y2][x2] == DESTINATION))
		{
			levelBoard[y2][x2] = BOXONDEST;
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
		}
		else if((levelBoard[y0][x0] == MANONDEST) && (levelBoard[y1][x1] == BOXONDEST) && (levelBoard[y2][x2] == DESTINATION))
		{
			levelBoard[y2][x2] = BOXONDEST;
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y][x] = DESTINATION;
			positionChange(x1, y1);
		}
		else if((levelBoard[y0][x0] == MANONDEST) && (levelBoard[y1][x1] == BOXONDEST) && (levelBoard[y2][x2] == FLOOR))
		{
			levelBoard[y2][x2] = BOX;
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y0][x0] = DESTINATION;
			positionChange(x1, y1);
			numberOfFreeBoxes++;
		}
		else if(levelBoard[y1][x1] == DESTINATION)
		{
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
		}
		else if((levelBoard[y1][x1] == BOXONDEST) && (levelBoard[y2][x2] == FLOOR))
		{
			levelBoard[y2][x2] = BOX;
			levelBoard[y1][x1] = MANONDEST;
			levelBoard[y0][x0] = FLOOR;
			positionChange(x1, y1);
			numberOfFreeBoxes++;
		}
		
	}
	//===============================================================================
	/** Metoda kieruj¹ca ruchem w dó³*/
	public void moveDown()
	{
		move(x, x, x, y, y+1, y+2);
		this.repaint();
		checkAllBoxesInDest();
	}
	//===============================================================================
	/** Metoda kieruj¹ca ruchem w górê */
	public void moveUp()
	{
		move(x, x, x, y, y-1, y-2);
		this.repaint();
		checkAllBoxesInDest();
	}
	//===============================================================================
	/** Metoda kieruj¹ca ruchem w lewo*/
	public void moveLeft()
	{
		move(x, x-1, x-2, y, y, y);
		this.repaint();
		checkAllBoxesInDest();
	}
	//===============================================================================
	/** Metoda kieruj¹ca ruchem w prawo*/
	public void moveRight()
	{
		move(x, x+1, x+2, y, y, y);
		this.repaint();
		checkAllBoxesInDest();
	}
	//===============================================================================
	/** Metoda wczytuj¹ca odpowiednie obrazki do poszczególnych pól */
	public void loadImages(ConfigReader con)
	{
		if(! new File(con.getWall()).exists())
		{
			throw new IllegalArgumentException("Plik " + con.getWall() + " nie istnieje.");
		}
		else if(!new File(con.getFloor()).exists())
		{
			throw new IllegalArgumentException("Plik " + con.getFloor() + " nie istnieje.");
		}
		else if(!new File(con.getMan()).exists())
		{
			throw new IllegalArgumentException("Plik " + con.getMan() + " nie istnieje.");
		}
		else if(!new File(con.getBox()).exists())
		{
			throw new IllegalArgumentException("Plik " + con.getBox() + " nie istnieje.");
		}
		else if(!new File(con.getDestination()).exists())
		{
			throw new IllegalArgumentException("Plik " + con.getDestination() + " nie istnieje.");
		}
		wallImage = Toolkit.getDefaultToolkit().getImage(con.getWall()).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		floorImage = Toolkit.getDefaultToolkit().getImage(con.getFloor()).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		destinationImage = Toolkit.getDefaultToolkit().getImage(con.getDestination()).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		manImage = Toolkit.getDefaultToolkit().getImage(con.getMan()).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		boxImage = Toolkit.getDefaultToolkit().getImage(con.getBox()).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(wallImage, 1);
		mt.addImage(floorImage, 2);
		mt.addImage(destinationImage, 3);
		mt.addImage(manImage, 4);
		mt.addImage(boxImage, 5);
		try{
			mt.waitForID(1);
			mt.waitForID(2);
			mt.waitForID(3);
			mt.waitForID(4);
			mt.waitForID(5);
		}catch(InterruptedException ie){
		}
		
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda zwracaj¹ca wysokoœæ klocków*/
	public int whatHeight()
	{
		return HEIGHT;
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda zwracaj¹ca szerokoœæ klocków */
	public int whatWidth()
	{
		return WIDTH;
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda zwracaj¹ca liczbê kolumn w tablicy opisuj¹cej plansze */
	public int nOfColumns()
	{
		return levelBoard.length;
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda zwracaj¹ca liczbê wierszy w tablicy opisuj¹cej plansze */
	public int nOfLine()
	{
		return levelBoard[0].length;
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda zliczaj¹ca liczbê pude³ek w poziomie */
	public void countFreeBoxes()
	{
		for(int i = 0; i< levelBoard.length; i++)
		{
			for(int j = 0; j < levelBoard[i].length; j++)
			{
				if(levelBoard[i][j] == BOX) 
					numberOfFreeBoxes++;
			}
		
		}
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda sprawdzaj¹ca czy wszystkie pude³ka s¹ na docelowym miejscu
	/* Jeœli tak, wyswietla okienko potwierdzajace ukonczenie poziomu */
	public void checkAllBoxesInDest()
	{
		if(numberOfFreeBoxes == 0)
		{
			LevelCompletedFrame l = new LevelCompletedFrame(1);
			add(l);
		}
	}

}


