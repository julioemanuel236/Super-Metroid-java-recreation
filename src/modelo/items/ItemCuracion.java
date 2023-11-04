package modelo.items;

import modelo.Mob;

public class ItemCuracion extends Item{

	public ItemCuracion() {
		super();
	}

	@Override
	public void usarSobre(Mob a) {
		//dar toda la vida a la entidad
		a.setVida(a.getVidaMaxima());		
		morir();
	}	
	
}
