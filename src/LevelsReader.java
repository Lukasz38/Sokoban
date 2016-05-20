import java.nio.file.*;
import java.util.Vector;
import java.io.*;
/** Klasa odpowiedzialna za wczytywanie wygl¹du poziomow gry z pliku */
public class LevelsReader {

	/** Nazwa pliku z którego wczytywana jest konfiguracja */
	private String levelsFilename;
	/** Wektor przchowujacy informacje o poziomach gry */
	public Vector<Level> levels = new Vector<Level>();
	//-------------------------------------------------------------------------------------------------------
	/** Konstruktor z parametrem
	 * 
	 * @param filename nazwa pliku z którego wczytywana jest konfiguracja
	 */
	LevelsReader(String filename)
	{
		levelsFilename = filename;
		readLevelsConfig();
	}
	//-------------------------------------------------------------------------------------------------------
	/** Funkcja wczytuj¹ca konfiguracjê z pliku */
	public void readLevelsConfig()
	{
		String content = null;
		Path path = Paths.get(levelsFilename);
		
		try
		{
			if(!Files.exists(path))
				throw new FileNotFoundException();
			BufferedReader reader = Files.newBufferedReader(path);
			
			do
			{
				content = reader.readLine();
				if((content != null) && (content.contains("#LEVEL")))
				{
					int columns = 0;
					int lines = 0;
					int score = 0;
					Level level = new Level();
					Vector<String> stringVector= new Vector<String>();
					
					content = reader.readLine();
					while((content != null) && (!content.contains("#LEVEL")))
					{
						if((content!=null) && (content.contains("#SCORE")))
						{
							content = reader.readLine();
							if(!content.trim().isEmpty())
							{
								score = Integer.parseInt(content);
								level.setScore(score);
							}
						}
						else if(!(content.trim().isEmpty()))
						{
							stringVector.addElement(content);
							columns = content.length();
							lines++;
						}
						content = reader.readLine();
					}
					
					level.setMapSize(lines, columns);
					convertToMap(stringVector,level);
					levels.addElement(level);
				}
			}while(content != null);		
		}
		catch(FileNotFoundException fnf)
		{
			fnf.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	//-------------------------------------------------------------------------------------------------------
	/** Funkcja pomocnicza, przetwarza wczytana konfiguracje 
	 * 
	 * @param vec 
	 * @param level
	 */
	private void convertToMap(Vector<String> vec, Level level)
	{
		char[] charArray;
		String temp;
		for(int i = 0; i <vec.size(); i++)
		{
			temp = vec.get(i);
			charArray = new char[temp.length()];
			charArray = temp.toCharArray();
			for(int j = 0; j< charArray.length; j++)
			{
				level.getMap()[i][j] = Character.getNumericValue(charArray[j]);
			}
		}
	}
	
	
}
