package Main;

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
	
	public static void main(String[] args) {
		/*TitleScreen screen = new TitleScreen();
		screen.setVisible(true);*/
		
		Game game = new Game();
	}

}
