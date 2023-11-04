package modelo.enemigo;

import java.awt.Image;
import java.util.Random;

import controlador.Global;
import modelo.List;

public class EnemyType1 extends Enemigo{

	public EnemyType1(List<Image> sprites) {
		super(sprites);		
		setVidaMaxima(10);
		setUpdateFrameRate(5);
		setHorizontalAlignment(CENTER);
	}

	public void update() {
		int x  = 0;
		if(Global.frame%getUpdateFrameRate() == 0) {
			update(getSprites().getNext());		
			Random rand = new Random();
			//seleccionar si moverse para la izquierda o derecha
			x = rand.nextInt(2);
			if(x == 0) x = -1;
			x*=2;			
		}
		moveTo(getX()+getxMove()+x,getY()+getyMove());
	}
	
	@Override
	public int getAtaque() {
		Random rand = new Random();		
		return super.getAtaque()+rand.nextInt(6);		
	}
}
