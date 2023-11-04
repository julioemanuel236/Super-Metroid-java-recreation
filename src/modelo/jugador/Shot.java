package modelo.jugador;

import controlador.*;
import modelo.Mob;

public class Shot extends Mob{

	/*
	 * esta clase representa los disparos
	 */
	
	public Shot() {
		setGravity(false);
		ShotControl.balas.add(this);		
	}

	public void impactar(Mob a) {
		a.recibirGolpe(this);
		this.morir();
	}
	
		
	public void move() {
		super.moveTo(getX()+getxMove(), getY() + getyMove());		
		//System.out.println(getxMove());
	}
	
}
