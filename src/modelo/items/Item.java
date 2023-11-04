package modelo.items;

import controlador.EntityControl;
import controlador.Global;
import controlador.ItemControl;
import controlador.PlayerControl;
import modelo.Mob;

public abstract class Item extends Mob{
	
	//cuantos pixeles ira hacia arriba
	int goUp = 20;	
	
	//cuantos ha subido
	int upped = 0;
	
	private boolean goinUp = true;
	
	public Item(){
		ItemControl.items.add(this);
		setGravity(false);
		setAtaque(0);
		setInmortal(true);
	}
		
	public void update() {
		//comprobar si se esta colisionando con el jugador
		EntityControl.colision(PlayerControl.getPlayer(), this);
		
		//omitir un frame si uno no
		if(Global.frame%2!=0)return;
				
		if(goinUp) {
			//si esta subiendo
			if(upped<goUp) {
				//mientras no haya llegado seguir  subiendo
				upped++;
				moveTo(getX(),getY()-1);
			}
			else goinUp = false;
		}
		else {
			//en caso de que este bajando
			if(upped>0) {
				//mientras que no haya llegado seguir bajando
				upped--;
				moveTo(getX(),getY()+1);
			}
			else goinUp = true;
		}
		
	}
	
	public abstract void usarSobre(Mob a);
				
}
