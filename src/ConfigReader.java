import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector; 

/** Klasa wczytuj¹ca i trzymaj¹ca dane  z pliku .properties */
public class ConfigReader  {
	
	InputStream inputStream;
	/** obraz na g³ówne t³o */
	private String mainbackground;
	/** obrazki poszczególnych klocków -> sciana, pod³oga, box itd */
	private String box, destination, floor, wall, man, bonus;
	/**  nazwy graczy */
	private String[] names; 
	/** iloœæ graczy */
	private int nOfPlayers;
	/** numery najdalej odblokowanych poziomów wszystkich graczy */
	private int[] maxUnlockedLevel;
	/** wyniki wszystkich graczy z poszczególnych poziomów */
	private int[][] scores; // [liczba graczy][liczba leveli]
	/** liczba poziomów */
	private int levels;
	/** tablica pomocnicza */
	private int[] score;
	/** obiekt klasy wczytuj¹cej konfiguracje poziomów gry */
	public LevelsReader lr = new LevelsReader("levels.txt");
	/** metoda wczytuj¹ca z pliku */
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
	/** metoda zwracaj¹ca nazwe obrazka klocka œciany */
	public String getWall()
	{
		return wall; 
	}
	/** metoda zwracaj¹ca nazwe obrazka t³a menu g³ównego */
	public String getMainPanel()
	{
		return mainbackground;
	}

	/** metoda zwracaj¹ca nazwe obrazka klocka pod³ogi */
	public String getFloor()
	{
		return floor;
	}
	/** metoda zwracaj¹ca nazwe obrazka klocka na którym ma staæ paczka docelowo */
	public String getDestination()
	{
		return destination;
	}
	/** metoda zwracaj¹ca nazwe obrazka paczki */
	public String getBox()
	{
		return box;
	}
	/** metoda zwracaj¹ca nazwe obrazka bonus */
	public String getBonus()
	{
		return bonus;
	}
	/**metoda zwracaj¹ca nazwe obrazka man*/
	public String getMan()
	{
		return man;
	}

//=============Players==================
	
	/** metoda zwracaj¹ca nazwe gracza */
	public String getName(int i)
	{
		return names[i];
	}
	/** metoda zwracaj¹ca liczbe odblokowanych poziomów gracza */
	public int getUnlockedLevel(int i)
	{
		return maxUnlockedLevel[i];
	}
	/** metoda zwracaj¹ca tablice wyników gracza z poszczególnych poziomów */
	public int[] getScores(int id)
	{
		score = scores[id-1];
		return score;
	}
	
}


