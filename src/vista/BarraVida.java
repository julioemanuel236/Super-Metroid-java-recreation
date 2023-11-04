package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import controlador.UIControl;

public class BarraVida extends JLabel{

	private int vidaMaxima;
	private int vidaActual;
	private int vidaObjetivo;
	private Font font = new Font("Arial",Font.BOLD,30);
	
	public BarraVida(int width,int height) {
		setSize(width,height);		
		setOpaque(false);
		UIControl.ui.add(this);
	}
	
	public int getVidaMaxima() {
		return vidaMaxima;
	}
	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}
	public int getVidaActual() {
		return vidaActual;
	}
	public void setVidaActual(int vidaActual) {
		this.vidaActual = vidaActual;
	}
			
	public int getVidaObjetivo() {
		return vidaObjetivo;
	}

	public void setVidaObjetivo(int vidaObjetivo) {
		this.vidaObjetivo = vidaObjetivo;		
	}

	@Override
	public void paint(Graphics g) {				
		/*
		 *  para dibujar la barra de vida
		 *  se calcula el porcenta de la vida actual
		 *  con el de la vida maxima
		 *  ese porcentaje sera el porcentaje
		 *  del ancho del componente que vamos a pintar
		 *  de un color
		 *  primero se pinta un rectangulo negro con trasnparencia
		 *  en todo el componente
		 *  luego se elije un color que varia de rojo a verde
		 *  usando el porcentaje de vida como porcentaje
		 *  de r  y g en el componente rgb del color
		 *  luego se da fuente al texto que es vida actual / vida maxima
		 *  se centra el texto en el componente y se pinta
		 *  luego si la vida actual es diferente de la vida objetivo
		 *  se suma o resta 1 segun sea menor y mayor
		 */
		
		Graphics2D g2 = (Graphics2D)g;		
		String txt = "ENERGY "+vidaActual;
		if(vidaObjetivo<0)vidaObjetivo = 0;
		float porcentaje = (float)vidaActual/(float)vidaMaxima;
		Color color;
		
		int width = (int)(getWidth()*porcentaje);	
		int r = 255-((int)(255*porcentaje));
		int gr = ((int)(255*porcentaje));
		if(r<0)r = 0;
		else if(r>255)r = 255;
		if(gr<0)gr = 0;
		else if(gr>255)gr =255;
		color = new Color(r,gr,0);
		g2.setColor(new Color(0,0,0,100));		
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(color);				
		g2.fillRect(0,0, width , getHeight());					
		g2.setColor(Color.white);
		g2.setFont(font);
		Rectangle2D bounds = getFontMetrics(font).getStringBounds(txt, g2);
		g2.drawString(txt, (int)((getWidth()/2)-(bounds.getWidth()/2)), (int)bounds.getHeight());

		if(vidaObjetivo!=vidaActual) {
			if(vidaObjetivo>vidaActual)vidaActual++;
			else vidaActual--;
			try{
				Thread.sleep(2);
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}
	}

}
