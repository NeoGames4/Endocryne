package Main;

import java.io.File;

/**
 * Die Launcher-Klasse, welche das Programm startet.
 * <p>Hier wird gerade gestartet; das Spiel (statt des TitleScreens, wie sp�ter beabsichtigt).
 * @author Mika Thein
 * @author Felix B�ttcher
 * @author Max Tarasova
 * @version 1.0
 * @see {@link Game}
 */
public class Launcher {
	
	/**
	 * Ob der Debug-Modus aktiviert sein soll.
	 */
	public static boolean DEBUG = true;
	
	/**
	 * Der Speicherpfad für Endocryne-Dateien wie beispielsweise Welten und Einstellungen.
	 */
	public static final String mainDirectoryPath = System.getProperty("user.home") + "/Endocryne/";
	
	public static void main(String[] args) {
		// TitleScreen screen = new TitleScreen();
		
		File settings = new File(mainDirectoryPath + "settings.json");
		if(!settings.exists()) {
			settings.getParentFile().mkdirs();
			try {
				settings.createNewFile();
			} catch(Exception e) { e.printStackTrace(); }
			System.out.println("Created " + mainDirectoryPath + "settings.json");
		}
		File worldsDirectory = new File(mainDirectoryPath + "worlds");
		if(!worldsDirectory.exists()) {
			worldsDirectory.mkdirs();
			System.out.println("Created " + mainDirectoryPath + "worlds/");
		}
		
		Game game = new Game();
	}

}
