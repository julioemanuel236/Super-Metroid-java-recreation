package modelo.enemigo;

import java.awt.Image;
import java.util.Random;

import controlador.Global;
import controlador.PlayerControl;
import modelo.List;
import modelo.Mob;
import modelo.jugador.Samus;

public class EnemyType2 extends Enemigo{
	/*
	 * este es el tipo de enemigo que cuelga del techo y cae 
	 */
	private Image falling_sprite;
	
	public EnemyType2(List<Image> sprites,Image falling_sprite) {
		super(sprites);
		this.falling_sprite = falling_sprite;
		setGravity(false);				
		setInmortal(true);		
		setInAir(false);
		setAtaque(30);
		setVidaMaxima(30);
		setHorizontalAlignment(CENTER);
	}

	@Override
	public void update() {
		Samus player = PlayerControl.getPlayer();			
		//si esta en el aire dejar de ser inmortal
		if(isInAir())setInmortal(false);
		if(player.getX()+10>=getX()-player.getWidth() &&
		   player.getX()-10<=getX()+getWidth() &&
		   player.getY()> getY() && player.getY()-getY()<=250&& !haveGravity()) {
			//si esta cerca del jugador tirarse para caerle arriba
			//cae por gravedad
			//solo poner que tenga gravedad, y establecerlo en el aire
			//bajarlo 30 pixeles para sacarla de la colision con el techo del que cuelga
			setGravity(true);			
			setInmortal(false);
			setInAir(true);
			setLocation(getX(),getY()+30);
			
		}		 
		//poner sprite segun si esta cayendo o esta colgando
		if(haveGravity())update(falling_sprite);
		else if(Global.frame%getUpdateFrameRate() == 0)update(getSprites().getNext());
		updateLocation();
		
	}
	
	@Override
	public void atacar(Mob a) {
		setAtaque();//actualizar valor de ataque segun si esta en el aire o no
		a.recibirGolpe(this);
		this.morir();
	}
				
	public void setAtaque() {
		int multiplicador = 1;		
		if(isInAir())multiplicador = 2;		
		super.setAtaque(getAtaque()*multiplicador);
	}
	
}
