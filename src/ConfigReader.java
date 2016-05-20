import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector; 

/** Klasa wczytuj�ca i trzymaj�ca dane  z pliku .properties */
public class ConfigReader  {
	
	InputStream inputStream;
	/** obraz na g��wne t�o */
	private String mainbackground;
	/** obrazki poszczeg�lnych klock�w -> sciana, pod�oga, box itd */
	private String box, destination, floor, wall, man, bonus;
	/**  nazwy graczy */
	private String[] names; 
	/** ilo�� graczy */
	private int nOfPlayers;
	/** numery najdalej odblokowanych poziom�w wszystkich graczy */
	private int[] maxUnlockedLevel;
	/** wyniki wszystkich graczy z poszczeg�lnych poziom�w */
	private int[][] scores; // [liczba graczy][liczba leveli]
	/** liczba poziom�w */
	private int levels;
	/** tablica pomocnicza */
	private int[] score;
	/** obiekt klasy wczytuj�cej konfiguracje poziom�w gry */
	public LevelsReader lr = new LevelsReader("levels.txt");
	/** metoda wczytuj�ca z pliku */
	public void readConfiguration() throws IOException {
		
		lr.readLevelsConfig();
		try {
			Properties proper = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				proper.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			//==== get the property value==== 
			wall = proper.getProperty("wall");
			mainbackground = proper.getProperty("tlo");
			man = proper.getProperty("man");
			box = proper.getProperty("box");
			destination = proper.getProperty("destination");
			floor = proper.getProperty("floor");
			bonus = proper.getProperty("bonus");
			nOfPlayers = Integer.parseInt(proper.getProperty("players"));
			names = new String[nOfPlayers];
			for (int a=0; a<nOfPlayers; a++)
			{
				names[a]=proper.getProperty("name" + (a+1));
			}
			maxUnlockedLevel = new int[nOfPlayers];
			for (int a=0; a<nOfPlayers; a++)
			{
				maxUnlockedLevel[a]= Integer.parseInt(proper.getProperty("unlocked" + (a+1)));
			} 
			
			levels= Integer.parseInt(proper.getProperty("levels")); 
			
			scores = new int[nOfPlayers][levels];
			
			for (int a=0; a<nOfPlayers; a++)
			{
				//score(a) = 1;
				for (int b=0; b<levels; b++)
				{
					scores[a][b]=Integer.parseInt(proper.getProperty((b+1) + "level" + (a+1)));
				}
				
			} 
			System.out.println("1 gracz level 2: " + scores[0][1]);
			System.out.println("2 gracz level 1: " + scores[1][0]);   
			
		
			
			} 
		catch (Exception e) {
			System.out.println("Exception: " + e);
			} 
		finally {
			inputStream.close();
			}
		
	}
//==================moje testowanko==================================
	/*public static void main(String[] args) throws IOException {
		//proper properties = new proper();
		//properties.getPropValues();
		ConfigReader co = new ConfigReader();
		co.readConfiguration();
		co.getScores(2);
		
		} 
	
	*/
//==========Images========================
	/** metoda zwracaj�ca nazwe obrazka klocka �ciany */
	public String getWall()
	{
		return wall; 
	}
	/** metoda zwracaj�ca nazwe obrazka t�a menu g��wnego */
	public String getMainPanel()
	{
		return mainbackground;
	}

	/** metoda zwracaj�ca nazwe obrazka klocka pod�ogi */
	public String getFloor()
	{
		return floor;
	}
	/** metoda zwracaj�ca nazwe obrazka klocka na kt�rym ma sta� paczka docelowo */
	public String getDestination()
	{
		return destination;
	}
	/** metoda zwracaj�ca nazwe obrazka paczki */
	public String getBox()
	{
		return box;
	}
	/** metoda zwracaj�ca nazwe obrazka bonus */
	public String getBonus()
	{
		return bonus;
	}
	/**metoda zwracaj�ca nazwe obrazka man*/
	public String getMan()
	{
		return man;
	}

//=============Players==================
	
	/** metoda zwracaj�ca nazwe gracza */
	public String getName(int i)
	{
		return names[i];
	}
	/** metoda zwracaj�ca liczbe odblokowanych poziom�w gracza */
	public int getUnlockedLevel(int i)
	{
		return maxUnlockedLevel[i];
	}
	/** metoda zwracaj�ca tablice wynik�w gracza z poszczeg�lnych poziom�w */
	public int[] getScores(int id)
	{
		score = scores[id-1];
		return score;
	}
	
}


