package Main;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Die Launcher-Klasse, welche das Programm startet.
 * <p>
 * Hier wird gerade gestartet; das Spiel (statt des TitleScreens, wie sp�ter
 * beabsichtigt).
 * 
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
	 * Der Speicherpfad für Endocryne-Dateien wie beispielsweise Welten und
	 * Einstellungen.
	 */
	public static final String mainDirectoryPath = System.getProperty("user.home") + "/Endocryne/";

	public static void main(String[] args) {

		/*Loadscreen loadscreen = new Loadscreen();
		try {
			Thread.sleep(15000);
			loadscreen.dispose();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		
		TitleScreen titlescreen = new TitleScreen();
	    
		try {
			if(Options.skin == null) Options.skin = ImageIO.read(new File("./rsc/player.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		File settings = new File(mainDirectoryPath + "settings.json");
		if (!settings.exists()) {
			settings.getParentFile().mkdirs();
			try {
				settings.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Created " + mainDirectoryPath + "settings.json");
		}
		File worldsDirectory = new File(mainDirectoryPath + "worlds");
		if (!worldsDirectory.exists()) {
			worldsDirectory.mkdirs();
			System.out.println("Created " + mainDirectoryPath + "worlds/");
		}

		File skinsDirectory = new File(mainDirectoryPath + "skins");
		if (!skinsDirectory.exists()) {
			skinsDirectory.mkdirs();
			System.out.println("Created " + mainDirectoryPath + "skins/");
			try {
				ImageIO.write(ImageIO.read(new File("./rsc/mob.png")), "png",
						new File(skinsDirectory.getPath() + "/mob.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ImageIO.write(ImageIO.read(new File("./rsc/endocryneSkinTemplate.png")), "png",
						new File(skinsDirectory.getPath() + "/endocryneSkinTemplate.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				EntityImageSet.writeStandard(new File(skinsDirectory.getPath() + "/default.png"));
			} catch(IOException e) { e.printStackTrace(); }
		}

		// Game game = new Game();
	}

}
