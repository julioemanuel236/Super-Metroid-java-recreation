package controlador;


import modelo.*;
import modelo.enemigo.Enemigo;
import modelo.items.Item;
import modelo.jugador.Samus;
import modelo.jugador.Shot;
import modelo.mapa.Struct;
import modelo.mapa.StructCabina;


import javax.swing.ImageIcon;

public class EntityControl {
	
	public static boolean colision(Mob a,Mob b) {
		/*
		 * esta funciona recibe dos entidades
		 * y retorna si esta colisionando o no
		 * a parte de hacer otras funciones en depencia de que
		 * tipo de entidades sean	
		 */
		//u hace referencia a que (a) esta encima de (b)
		//d hace referencia a que (a) esta debajo de (b)
		//r hace referencia a que (a) esta a la derecha de (b)
		//l hace feferencia a que (a) esta a la derecha de (b)
		//touch es si se estan tocando en general
				
		boolean u,d,r,l,touch;		
		
		u = a.getBottomBounds().intersects(b.getTopBounds());
		l = a.getRightBounds().intersects(b.getLeftBounds()); 
		r = a.getLeftBounds().intersects(b.getRightBounds()); 
		d = a.getTopBounds().intersects(b.getBottomBounds()); 		
		touch = a.getBounds().intersects(b.getBounds());
		
		//analizando un disparo
		if(a instanceof Shot) {
			//si toca otro disparo ignorar
			if(b instanceof Shot)return false;
			if(touch) {
				//si toca, pero al jugador, ignorar
				if(!(b instanceof Samus)) {
					//impactar y devolver true
					((Shot)a).impactar(b);
					return true;
				}				
			}
			//si no no choca
			return false;
		}
				
		//si a no es un enemigo y b si y sse estan tocando, a toma el danno de b
		if(b instanceof Enemigo && !(a instanceof Enemigo) && touch) {
			((Enemigo)b).atacar(a);
			return true;
		}
		
		//si a es el jugador y b es un item el item sera usado sobre el jugador
		if(a instanceof Samus && b instanceof Item && touch) {
			((Item)b).usarSobre(a);			
			return false;
		}
		
		//si b es una estructura y a lo esta tocando por encima
		if(b instanceof Struct && u) {
			//decir que a ya no esta en el aire
			a.setInAir(false);
			//si es samus decir que no esta saltando
			if(a instanceof Samus) {
				((Samus)a).setJumping(false);
			}
		} 
		
		//si a es el jugador y b es la cabina de la nave
		if(a instanceof Samus && b instanceof StructCabina && touch) {			
			Global.win = true;
			return false;
		}
										
		//si toca por encima o debajo mantener la Y anterior y permitir movimiento en la X
		if (u || d)	a.setLocation(a.getX(),a.getLastY());					
			
		//si toca por los costados mantener la X anterior y permitir movimiento en la Y
		if(r || l) a.setLocation(a.getLastX(),a.getY());						
		
		//si se estan tocando y no es ninguna de las anteriores
		if (touch && !u && !d && !r && !l) {
			a.setLocation(a.getLastX(),a.getLastY());
			if(a.getLastImg() != null)
				a.setIcon(new ImageIcon(a.getLastImg()));
		}

		//retorna si hay colision de algun tipo
		return u || d || r || l || touch;
		
		
	}	

}
