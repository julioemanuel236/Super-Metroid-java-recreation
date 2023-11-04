package modelo.items;

import modelo.Mob;
import modelo.jugador.Samus;

public class ItemMorphoSphere extends Item{

	public ItemMorphoSphere() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void usarSobre(Mob a) {
		if(a instanceof Samus) {
			//si lo usa samus permitir que se haga bolita
			((Samus)a).setCanMakeBall(true);
			morir();
		}		
	}

	
}
