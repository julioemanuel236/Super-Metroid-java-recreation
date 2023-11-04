package controlador;

public class Global {
	/*
	 * variables globales 
	 */
	public static long frame = 0;
	
	public static boolean pause;

	public static boolean win;
	
	public static boolean gameOver = false;
	
	public static boolean canQuitMessageScreen() {
		if(gameOver)return false;
		if(win)return false;
		return true;
	}
}
