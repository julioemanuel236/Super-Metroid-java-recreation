package modelo.mapa;

import java.awt.Image;

import modelo.enemigo.Enemigo;

public class Pincho extends Enemigo{
	
	
	/*
	 * tipo de estructura que hace danno cuando
	 * una entidad lo toca por encima
	 */
	public Pincho() {
		super(null);
		setOpaque(false);
		setGravity(false);
		setAtaque(10);
	}

	@Override
	public void update() {
		
	}

	
	
}
