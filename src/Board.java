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
			move('u');
			break;
		case KeyEvent.VK_DOWN:
			move('d');
			break;
		case KeyEvent.VK_LEFT:
			move('l');
			break;
		case KeyEvent.VK_RIGHT:
			move('r');
			break;
		default:
			break;
		}
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda kieruj¹ca ruchem postaci */
	public void move(char whereToGo)
	{
		switch(whereToGo)
		{
		case 'u':
			if(levelBoard[y-1][x] == WALL);
			else if((levelBoard[y][x] == MAN) && (levelBoard[y-1][x] == FLOOR))
			{
				levelBoard[y-1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y-1][x] == DESTINATION))
			{
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y-1][x] == FLOOR))
			{
				levelBoard[y-1][x] = MAN;
				levelBoard[y][x] = DESTINATION;
				y--;
			}
			else if((levelBoard[y-1][x] == BOX) && (levelBoard[y-2][x] == FLOOR))
			{
				levelBoard[y-2][x] = BOX;
				levelBoard[y-1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y--;
			}
			else if((levelBoard[y-1][x] == BOX) && (levelBoard[y-2][x] == DESTINATION))
			{
				levelBoard[y-2][x] = BOXONDEST;
				levelBoard[y-1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y--;
				numberOfFreeBoxes--;
			}
			else if((levelBoard[y-1][x] == BOXONDEST) && (levelBoard[y-2][x] == DESTINATION))
			{
				levelBoard[y-2][x] = BOXONDEST;
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y-1][x] == BOXONDEST) && (levelBoard[y-2][x] == DESTINATION))
			{
				levelBoard[y-2][x] = BOXONDEST;
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y-1][x] == BOXONDEST) && (levelBoard[y-2][x] == 	FLOOR))
			{
				levelBoard[y-2][x] = BOX;
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y--;
				numberOfFreeBoxes++;
			}
			else if(levelBoard[y-1][x] == DESTINATION)
			{
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y--;
			}
			else if((levelBoard[y-1][x] == BOXONDEST) && (levelBoard[y-2][x] == FLOOR))
			{
				levelBoard[y-2][x] = BOX;
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y--;
				numberOfFreeBoxes++;
			}
			break;

			//DOWN=====================================================================
		case 'd':
			if(levelBoard[y+1][x] == WALL);
			else if((levelBoard[y][x] == MAN) && (levelBoard[y+1][x] == FLOOR))
			{
				levelBoard[y+1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y+1][x] == DESTINATION))
			{
				levelBoard[y+1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y+1][x] == FLOOR))
			{
				levelBoard[y+1][x] = MAN;
				levelBoard[y][x] = DESTINATION;
				y++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y+1][x] == BOX) && (levelBoard[y+2][x] == FLOOR))
			{
				levelBoard[y+2][x] = BOX;
				levelBoard[y+1][x] = MAN;
				levelBoard[y][x] = DESTINATION;
				y++;
			}
			else if((levelBoard[y+1][x] == BOX) && (levelBoard[y+2][x] == FLOOR))
			{
				levelBoard[y+2][x] = BOX;
				levelBoard[y+1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y++;
			}
			else if((levelBoard[y+1][x] == BOX) && (levelBoard[y+2][x] == DESTINATION))
			{
				levelBoard[y+2][x] = BOXONDEST;
				levelBoard[y+1][x] = MAN;
				levelBoard[y][x] = FLOOR;
				y++;
				numberOfFreeBoxes--;
			}
			else if((levelBoard[y+1][x] == BOXONDEST) && (levelBoard[y+2][x] == DESTINATION))
			{
				levelBoard[y+2][x] = BOXONDEST;
				levelBoard[y+1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y+1][x] == BOXONDEST) && (levelBoard[y+2][x] == DESTINATION))
			{
				levelBoard[y-2][x] = BOXONDEST;
				levelBoard[y-1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y+1][x] == BOXONDEST) && (levelBoard[y+2][x] == 	FLOOR))
			{
				levelBoard[y+2][x] = BOX;
				levelBoard[y+1][x] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				y++;
				numberOfFreeBoxes++;
			}
			else if(levelBoard[y+1][x] == DESTINATION)
			{
				levelBoard[y+1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y++;
			}
			else if((levelBoard[y+1][x] == BOXONDEST) && (levelBoard[y+2][x] == FLOOR))
			{
				levelBoard[y+2][x] = BOX;
				levelBoard[y+1][x] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				y++;
				numberOfFreeBoxes++;
			}
			break;

			//LEFT=====================================================================
		case 'l':
			if(levelBoard[y][x-1] == WALL);
			else if((levelBoard[y][x] == MAN) && (levelBoard[y][x-1] == FLOOR))
			{
				levelBoard[y][x-1] = MAN;
				levelBoard[y][x] = FLOOR;
				x--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x-1] == BOX) && (levelBoard[y][x-2] == FLOOR))
			{
				levelBoard[y][x-2] = BOX;
				levelBoard[y][x-1] = MAN;
				levelBoard[y][x] = DESTINATION;
				x--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x-1] == FLOOR))
			{
				levelBoard[y][x-1] = MAN;
				levelBoard[y][x] = DESTINATION;
				x--;
			}
			else if((levelBoard[y][x-1] == BOX) && (levelBoard[y][x-2] == FLOOR))
			{
				levelBoard[y][x-2] = BOX;
				levelBoard[y][x-1] = MAN;
				levelBoard[y][x] = FLOOR;
				x--;
			}
			else if((levelBoard[y][x-1] == BOX) && (levelBoard[y][x-2] == DESTINATION))
			{
				levelBoard[y][x-2] = BOXONDEST;
				levelBoard[y][x-1] = MAN;
				levelBoard[y][x] = FLOOR;
				x--;
				numberOfFreeBoxes--;
			}

			else if((levelBoard[y][x] == BOXONDEST) && (levelBoard[y][x-2] == DESTINATION))
			{
				levelBoard[y][x-2] = BOXONDEST;
				levelBoard[y][x-1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x-1] == BOXONDEST) && (levelBoard[y][x-2] == DESTINATION))
			{
				levelBoard[y][x-2] = BOXONDEST;
				levelBoard[y][x-1] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				x--;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x-1] == BOXONDEST) && (levelBoard[y][x-2] == 	FLOOR))
			{
				levelBoard[y][x-2] = BOX;
				levelBoard[y][x-1] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				x--;
				numberOfFreeBoxes++;
			}
			else if(levelBoard[y][x-1] == DESTINATION)
			{
				levelBoard[y][x-1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x--;
			}
			else if((levelBoard[y][x-1] == BOXONDEST) && (levelBoard[y][x-2] == FLOOR))
			{
				levelBoard[y][x-2] = BOX;
				levelBoard[y][x-1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x--;
				numberOfFreeBoxes++;
			}
			break;
			
			//RIGHT=====================================================================
		case 'r':
			if(levelBoard[y][x+1] == WALL);
			else if((levelBoard[y][x] == MAN) && (levelBoard[y][x+1] == FLOOR))
			{
				levelBoard[y][x+1] = MAN;
				levelBoard[y][x] = FLOOR;
				x++;
			}
			else if((levelBoard[y][x+1] == BOX) && (levelBoard[y][x+2] == FLOOR))
			{
				levelBoard[y][x+2] = BOX;
				levelBoard[y][x+1] = MAN;
				levelBoard[y][x] = FLOOR;
				x++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x+1] == FLOOR))
			{
				levelBoard[y][x+1] = MAN;
				levelBoard[y][x] = DESTINATION;
				x++;
			}
			else if((levelBoard[y][x+1] == BOX) && (levelBoard[y][x+2] == FLOOR))
			{
				levelBoard[y][x+2] = BOX;
				levelBoard[y][x+1] = MAN;
				levelBoard[y][x] = FLOOR;
				x++;
			}
			else if((levelBoard[y][x+1] == BOX) && (levelBoard[y][x+2] == DESTINATION))
			{
				levelBoard[y][x+2] = BOXONDEST;
				levelBoard[y][x+1] = MAN;
				levelBoard[y][x] = FLOOR;
				x++;
				numberOfFreeBoxes--;
			}
			else if((levelBoard[y][x] == BOXONDEST) && (levelBoard[y][x+2] == DESTINATION))
			{
				levelBoard[y][x+2] = BOXONDEST;
				levelBoard[y][x+1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x+1] == BOXONDEST) && (levelBoard[y][x+2] == DESTINATION))
			{
				levelBoard[y][x+2] = BOXONDEST;
				levelBoard[y][x+1] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				x++;
			}
			else if((levelBoard[y][x] == MANONDEST) && (levelBoard[y][x+1] == BOXONDEST) && (levelBoard[y][x+2] == 	FLOOR))
			{
				levelBoard[y][x+2] = BOX;
				levelBoard[y][x+1] = MANONDEST;
				levelBoard[y][x] = DESTINATION;
				x++;
				numberOfFreeBoxes++;
			}
			else if(levelBoard[y][x+1] == DESTINATION)
			{
				levelBoard[y][x+1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x++;
			}
			else if((levelBoard[y][x+1] == BOXONDEST) && (levelBoard[y][x+2] == FLOOR))
			{
				levelBoard[y][x+2] = BOX;
				levelBoard[y][x+1] = MANONDEST;
				levelBoard[y][x] = FLOOR;
				x++;
				numberOfFreeBoxes++;
			}
			break;
		}
		
		this.repaint();
		checkAllBoxesInDest();
	}
	//-------------------------------------------------------------------------------------------------------
	
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
			setVisible(false);
		}
	}

}


