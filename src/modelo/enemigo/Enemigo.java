package modelo.enemigo;

import java.awt.Image;

import controlador.*;
import modelo.List;
import modelo.Mob;

public abstract class Enemigo extends Mob{
	//cada cuantos frames se actualiza el frame de animacion
	private static int updateFrameRate = 7;
	
	//lista de sprites
	private List<Image> sprites;
			
	public Enemigo(List<Image> sprites) {		
		EnemyControl.enemigos.add(this);
		this.sprites = sprites;
	}

	public static int getUpdateFrameRate() {
		return updateFrameRate;
	}

	public static void setUpdateFrameRate(int updateFrameRate) {
		Enemigo.updateFrameRate = updateFrameRate;
	}

	public List<Image> getSprites() {
		return sprites;
	}

	public void setSprites(List<Image> sprites) {
		this.sprites = sprites;
	}
		
	public abstract void update();

	public void atacar(Mob a) {
		a.recibirGolpe(this);
	}
	
	public float getValor() {
		//porcentaje de vida actual del jugador multiplicado por la vida maxima del enemigo
		return getVidaMaxima()*((float)PlayerControl.getPlayer().getVida()/100f);
	}
}