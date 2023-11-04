package controlador;
import modelo.*;
import modelo.jugador.Shot;

import java.awt.Image;

import javax.imageio.ImageIO;

public class ShotControl {

	public static List<Shot> balas = new List<>();
	private static Image shot;

	public ShotControl() {
		cargarImagenes();
	}
	
	public void cargarImagenes() {
		try {
			shot = ImageIO.read(getClass().getResource("/images/player/shot.png"));
			shot = shot.getScaledInstance(shot.getWidth(null)*PlayerControl.SCALE, shot.getHeight(null)*PlayerControl.SCALE, Image.SCALE_SMOOTH);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		int n = balas.size();
		/*
		 * el mismo sistema de actualizacion de enemy e item
		 */
		//System.out.println(balas.size());
		while(n>0) {			
			Shot bala = balas.poll();			
			bala.move();
			if(!bala.isMuerto())balas.add(bala);			
			n--;
		}
	}
	
	public static Shot createNewShot(boolean dir,int x,int y) {
		Shot s = new Shot();
		if(dir) s.setdMove(25);
		
		else s.setaMove(25);
		
		s.update(shot);
		s.setLocation(x,y);
		return s;
	}
	
}
