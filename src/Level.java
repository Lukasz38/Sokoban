
/**Klasa przechowujaca konfiguracje jednego poziomu */
public class Level {
	/** Przechowuje wyglad poziomu */
	private int[][] map;
	/** Przechowuje numer poziomu */
	private int id;
	/** Przechowuje liczbe punktow za ukonczenie poziomu */
	private int score;
	/** Przechowuje liczbe wczytanych poziomow */
	private static int levelsNumber = 0;
	//-------------------------------------------------------------------------------------------------------
	
	/** Konstruktor */
	Level()
	{
		levelsNumber++;
		id = levelsNumber;
	}
	//-------------------------------------------------------------------------------------------------------
	
	/** Metoda ustalajaca wielkosc mapy 
	 * 
	 * @param height wysokosc mapy wyrazona w elementach
	 * @param width szerokosc mapy wyrazona w elementach
	 */
	public void setMapSize(int height, int width)
	{
		map = new int[height][width];
	}
	//-------------------------------------------------------------------------------------------------------
	/**Metoda zwracajaca mape poziomu
	 * 
	 * @return map mapa poziomu
	 */
	public int[][] getMap()
	{
		return map;
	}
	//-------------------------------------------------------------------------------------------------------
	
	public void printLvl()
	{
		for(int i = 0; i< map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				System.out.print(map[i][j]);
			}
			System.out.print("\n");
		}
		System.out.print(score);
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda ustawiajaca wynik do uzyskania za ukonczenie poziomu
	 * 
	 * @param score wynik do uzyskania za ukonczenie poziomu
	 */
	public void setScore(int score)
	{
		this.score = score;
	}
	//-------------------------------------------------------------------------------------------------------
	/** Metoda zwracajaca liczbe wczytanych poziomow
	 * 
	 * @return levelsNumber liczba wczytanych poziomow
	 */
	public int getLevelsNumber()
	{
		return levelsNumber;
	}
	
}
