package modelo.jugador;

import java.awt.Image;

import javax.swing.ImageIcon;

import controlador.Global;
import controlador.MapControl;
import controlador.ShotControl;
import modelo.Mob;

public class Samus extends Mob{

	/*
	 * clase que representa al jugador
	 * tiene los atributos necesarios para hacer 
	 * todas las acciones
	 * ademas algunas funciones como ver si puede disparar
	 * si puede hacerse bolita
	 * y la de recibir golpe que se usa polimorfismo
	 * para que tenga un breve periodo de invencibilidad
	 * ademas de la funcion de disparar
	 */
	
	private boolean jumping = false;
	private int jumpForce;
	private boolean godMode = false;
	private boolean haveToShot = false;
	private long lastShot = 0;
	private long shotDelay = 15;
	private boolean sit;
	private boolean canMakeBall = false;
	public Samus() {
		super();		
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	@Override
	public void setInAir(boolean inAir) {
		super.setInAir(inAir);
		
	}

	public int getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(int jumpForce) {
		this.jumpForce = jumpForce;
	}

	@Override
	public int getyMove() {
		int dy;
		if(!isGodMode())dy = jumpForce;
		else dy = 0;
		return super.getyMove()-dy;
	}
	
	public void setGodMode(boolean mode) {
		godMode = mode;
		if(mode) {
			setGravity(false);			
		}
		else setGravity(true);
	}

	public boolean isGodMode() {
		return godMode;
	}

	public boolean isHaveToShot() {
		return haveToShot;
	}

	public void setHaveToShot(boolean haveToShot) {
		this.haveToShot = haveToShot;
	}

	public long getLastShot() {
		return lastShot;
	}

	public void setLastShot(long lastShot) {
		this.lastShot = lastShot;
	}

	public long getShotDelay() {
		return shotDelay;
	}

	public void setShotDelay(long shotDelay) {
		this.shotDelay = shotDelay;
	}
	
	public boolean canShot() {			
		long now = Global.frame;			
		return now-lastShot>shotDelay;
	}
	
	public void shot() {
		int x = getX()-1;
		if(isFacingRight())x+=getWidth()+2;
		int y = getY();
		if(!isSit())y = y+(2*getHeight())/6;
		else y += 11*(getHeight()/20);
		Shot shot = ShotControl.createNewShot(isFacingRight(), x, y);
		MapControl.mapa.setLayer(shot, 2);
		MapControl.mapa.add(shot);
		lastShot = Global.frame;
	}

	public boolean isSit() {
		return sit;
	}

	public void setSit(boolean sit) {
		this.sit = sit;
	}
	
	@Override
	public void recibirGolpe(Mob a) {
		super.recibirGolpe(a);
		if(!isInvencible())setInvencible(true);
	}

	public boolean isCanMakeBall() {
		return canMakeBall;
	}

	public void setCanMakeBall(boolean canMakeBall) {
		this.canMakeBall = canMakeBall;
	}
	
	
}
