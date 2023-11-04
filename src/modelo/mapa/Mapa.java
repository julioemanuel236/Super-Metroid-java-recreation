package modelo.mapa;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import modelo.Mob;
import modelo.enemigo.Enemigo;
import modelo.jugador.Samus;
import modelo.jugador.Shot;

import java.awt.Image;
public class Mapa extends JLayeredPane{

	
	/*
	 * clase usada para tener representacion visual
	 * de los elementos en pantalla
	
	 */
	private Image fondoImg;
	private JLabel fondo = new JLabel();
	
	public Mapa(int width,int height) {
		
		setLayer(fondo,0);
		add(fondo);
		
	}
	
	public Mapa() {
		setLayout(null);
	}

	public Image getFondo() {
		return fondoImg;
	}

	public void setFondo(Image fondo) {
		
		this.fondoImg = fondo;
		this.fondo.setSize(fondoImg.getWidth(null),fondo.getHeight(null));
		this.fondo.setIcon(new ImageIcon(fondoImg));
		setSize(this.fondo.getSize());
		this.fondo.repaint();
		
	}
	
	public void add(Mob s) {
		int layer = 0;
		if(s instanceof Samus) layer = 10;
		else if(s instanceof Enemigo)layer = 2;
		else if(s instanceof Shot)layer = 3;
		else layer = 1;
		setLayer(s,10);
		super.add(s);
	}	
	
}
