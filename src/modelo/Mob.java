package modelo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import controlador.EnemyControl;
import controlador.EntityControl;
import controlador.Global;
import controlador.MapControl;
import modelo.mapa.Struct;

public abstract class Mob extends javax.swing.JLabel{
	
	/*
	 * clase base de todas las entidades del juego
	 * tiene la mayoria de los atributos ya que son comunes
	 * y funciones basicas como las del movimiento
	 * obtener el movimiento en los ejes x e y
	 * obtener los bordes de la entidad para usarlo en las colisiones
	 * actualizar la imagen verificando posiciones
	 * que no colisione con el mapa etc
	 */
	
	private int aMove = 0;
	private int dMove = 0;
	private int wMove = 0;
	private int sMove = 0;
	private boolean facing = true;
	private boolean gravity = true;
	private boolean inAir = false;
	private int initialGraviy = 1;
	private int gravityForce = initialGraviy;
	private int vidaMaxima = 100;
	private int vida = vidaMaxima;
	private int lastX,lastY;
	private int ataque = 10;
	private boolean invencible = false;
	private long invencibleFrames = 30;
	private long lastInvencibleFrame = 0;
	private boolean muerto = false;
	private boolean inmortal = false;
	private Image lastImg;
	
	public Mob() {
		//EntityControl.entidades.add(this);
	}
	
	public int getaMove() {
		return aMove;
	}

	public void setaMove(int aMove) {
		this.aMove = aMove;
	}

	public int getdMove() {
		return dMove;
	}

	public void setdMove(int dMove) {
		this.dMove = dMove;
	}

	public int getwMove() {
		return wMove;
	}

	public void setwMove(int wMove) {
		this.wMove = wMove;
	}

	public int getsMove() {
		return sMove;
	}

	public void setsMove(int sMove) {
		this.sMove = sMove;
	}

	public int getxMove() {
		if(dMove>aMove) {
			setFacingRight();
		}
		else if(dMove<aMove) {
			setFacingLeft();
		}
		
		return dMove-aMove ;
	}
	
	public int getyMove() {
		if(isInAir())gravityForce++;
		else gravityForce = initialGraviy ;
		return sMove-wMove + (haveGravity()?gravityForce:0);
		
	}

	public int getLastX() {
		return lastX;
	}

	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	public int getGravityForce() {
		return gravityForce;
	}

	public void setGravityForce(int gravity) {
		this.gravityForce = gravity;
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle(getX(),getY(),getWidth(),1);
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle(getX()-1,getY()+1,1,getHeight()-2);
	}
	
	public Rectangle getRightBounds() {		
		return new Rectangle(getX()+getWidth()+1,getY()+1,1,getHeight()-2);
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle(getX(),getY()+getHeight()-1,getWidth(),1);
	}
		
	public boolean haveGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public void update(Image img) {		
		//actualizacion de imagen'
		//calcular posicion de pies
		//actualizar tamanno e icono
		//poner posicion		
		if(Global.frame-getLastInvencibleFrame()>getInvencibleFrames())
			setInvencible(false);		
		if(img == null)return;		
		int y = this.getY()+this.getHeight();
		int nextY = (y-img.getHeight(null));
		int x = getX();
		int nextX = x;
		int dy = getyMove();
		//System.out.println(dy);
		if(isFacingRight()) {
			x = getX()+getWidth();
			nextX = (x-img.getWidth(null));
		}
		if(dy<=2 && isInAir()) {
			nextY = getY();
		}
		if(getHorizontalAlignment() == CENTER) {
			nextX = getX()+(getWidth()/2)-(img.getWidth(null)/2);
		}

		this.setSize(0,0);
		this.setLocation(nextX,nextY);
		this.setSize(img.getWidth(null), img.getHeight(null));					
		this.setIcon(new ImageIcon(img));
		
		boolean ok = true;
		
		for(int i = 0;i<MapControl.bloques.size();i++) {
			if(EntityControl.colision(this, MapControl.bloques.getNext())) {
				ok =false;
				break;
			}
			
		}
		if(ok) {
			this.setLastImg(img);			
			this.setLastY(nextY);
			this.setLastX(nextX);
		}
		
		else {
			update(getLastImg());			
		}
		if(isInvencible() && Global.frame%5 == 0)setVisible(!isVisible());
	}
	
	public void moveTo(int x,int y) {
		/*
		 * esta funcion mueve hacia las coordenas x e y
		 * mueve pixel por pixel
		 * primero mueve uno en x
		 * luego uno en y
		 * si toca algun eje que no pueda seguir moviendo 
		 * detiene el movimiento en ese eje
		 * tambien comprueba las colisiones con todas las structs del mapa
		 * y los enemigos
		 */
		setLastX(getX());
		setLastY(getY());

		int n = Math.abs(x-getX());
		int m = Math.abs(y-getY());		
		
		while(n>0 || m>0) {
			if(isMuerto())return;
			if(n>0) {
				n--;
				int dx = x>getX()?1:x==getX()?0:-1;								
				boolean ok = true;
				
				setLocation(getX()+dx,getY());
				for(int j=0;j<MapControl.bloques.size();j++) {									
					Struct b = MapControl.bloques.getNext();
					if(this==b)continue;
					if(EntityControl.colision(this,b)) {
						ok = false;
						n=0;
						break;
						
					}
				}
				if(ok) {
					setLastX(getX());
				}
				else setLocation(getLastX(),getY());
							
				//comprobar choque con otras unidades
				for(int j=0;j<EnemyControl.enemigos.size();j++) {									
					Mob b = EnemyControl.enemigos.getNext();
					if(this==b)continue;
					EntityControl.colision(this,b);
				}				
				
			}
			
			if(isMuerto())return;
						
			int dy = y>getY()?1:y==getY()?0:-1;
			if(m <= 0) {				
				if(!haveGravity())dy = 0;
				else dy = 1;
			}
			
			boolean ok = true;
			
			setLocation(getX(),getY()+dy);
			for(int j=0;j<MapControl.bloques.size();j++) {									
				Mob b = MapControl.bloques.getNext();
				if(this==b)continue;
				if(EntityControl.colision(this,b)) {
					ok = false;
					m=0;
					break;
				}
			}
			
			if(ok) {
				setLastY(getY());
				setInAir(true);
			}
			else setLocation(getX(),getLastY());

			for(int j=0;j<EnemyControl.enemigos.size();j++) {									
				Mob b = EnemyControl.enemigos.getNext();
				if(this==b)continue;
				EntityControl.colision(this,b);
			}				
			m--;
		}
	}

	public boolean isInAir() {
		return inAir;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;		
	}

	public boolean isGravity() {
		return gravity;
	}
	
	public void updateLocation() {		
		moveTo(getX()+getxMove(),getY()+getyMove());
	}

	public boolean isFacing() {
		return facing;
	}

	public boolean isFacingRight() {
		return facing;
	}
	
	public boolean isFacingLeft() {
		return !facing;
	}
	
	public void setFacingRight() {
		facing = true;
	}
	
	public void setFacingLeft() {
		facing = false;
	}
	
	public void setFacing(boolean facing) {
		this.facing = facing;
	}

	public int getInitialGraviy() {
		return initialGraviy;
	}

	public void setInitialGraviy(int initialGraviy) {
		this.initialGraviy = initialGraviy;
	}

	public Image getLastImg() {
		return lastImg;
	}

	public void setLastImg(Image lastImg) {
		this.lastImg = lastImg;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
		if(vida>vidaMaxima)vida = vidaMaxima;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public void recibirGolpe(Mob m) {
		if(isInvencible() || isInmortal())return;
		vida -= m.getAtaque();
		//System.out.println(getClass().getSimpleName()+" "+vida);
		if(vida<=0)morir();
	}

	public void morir() {		
		setMuerto(true);
		setVisible(false);		
	}
	
	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public boolean isMuerto() {
		return muerto;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}

	public boolean isInvencible() {
		return invencible;
	}

	public void setInvencible(boolean invencible) {
		this.invencible = invencible;
		if(invencible)
			setLastInvencibleFrame(Global.frame);		
		else if(!isMuerto())setVisible(true);
	}

	public long getInvencibleFrames() {
		return invencibleFrames;
	}

	public void setInvencibleFrames(long invencibleFrames) {
		this.invencibleFrames = invencibleFrames;
	}

	public long getLastInvencibleFrame() {
		return lastInvencibleFrame;
	}

	public void setLastInvencibleFrame(long lastInvencibleFrame) {
		this.lastInvencibleFrame = lastInvencibleFrame;
	}

	public boolean isInmortal() {
		return inmortal;
	}

	public void setInmortal(boolean inmortal) {
		this.inmortal = inmortal;
	}
			
	
}
