package modelo.mapa;

import controlador.MapControl;
import modelo.Mob;

public class Struct extends Mob{
	/*
	 * estructura base para representar el mapa
	 * ya sea suelo paredes y techo
	 */
	public Struct(){
		super();	
		MapControl.bloques.add(this);
		setGravity(false);
	//	setOpaque(true);
		setBackground(java.awt.Color.white);
		setInmortal(true);		
	}
			
}
