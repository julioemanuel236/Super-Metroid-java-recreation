package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controlador.UIControl;

public class Message extends JLabel{
	
	private Font font = new Font("Arial",Font.BOLD,100);
		
	private String message;
	
	private Color color;
		
	private int alpha = 150;
	
	public Message(String s) {
		this.message = s;				
		setOpaque(false);		
	}
	
	@Override
	public void paint(Graphics g) {				
		Graphics2D g2 = (Graphics2D)g;
		/*
		 * se pinta un fondo negro
		 * y encima el cartel del color que se establecio
		 * se centra con el mismo sistema usado en BarraVida 
		 */
		//poner fondo		
		g2.setColor(new Color(0,0,0,alpha));
		g2.fillRect(0,0,getWidth(),getHeight());
		
		//poner las letras		
		Rectangle2D bounds = getFontMetrics(font).getStringBounds(message, g2);				
		if(color != null) {
			g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
		}
		else g2.setColor(new Color(255,255,255,alpha));
		g2.setFont(font);
		g2.drawString(message, (int)((getWidth()/2)-(bounds.getWidth()/2)), (getHeight()/2)+(int)(bounds.getHeight()/2));						
		
	}
		
	public void addTo(Container c) {		
		setSize(c.getSize());
		/*
		 * dar el tamanno del componente
		 * si es un layeredPane se pone en la capa 15
		 * sino se agrega en la posicion 0
		 */
		
		if(c instanceof JLayeredPane) {
			JLayeredPane lp = (JLayeredPane)c;
			lp.setLayer(this,15);			
			lp.add(this);
		}
		else c.add(this,0);
		repaint();
	}
	
	public void quit() {
		/*
		 * quitar del componente padre
		 * en caso de que tenga padre
		 * intentar repintar
		 */
		Container c = getParent();
		if(c!=null)c.remove(this);
		try{
                    c.repaint();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
		
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public Color getColor() {
		return color;
	}
	
	
}
