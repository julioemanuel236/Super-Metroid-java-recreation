package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import modelo.Action;

public abstract class MessageEntry extends Message implements Action{

	public MessageEntry(String s) {		
		super(s);
		setFont(new Font("Arial",Font.BOLD,50));
	}
	
	@Override
	public void paint(Graphics g) {				
		/*
		 * mismo diseño que la clase padre
		 */
		Graphics2D g2 = (Graphics2D)g;
		Color color = getColor();
		Font font = getFont();
		String message = getMessage();
		
		int alpha = getAlpha();
		//poner fondo		
		g2.setColor(new Color(0,0,0,alpha));
		g2.fillRect(0,0,getWidth(),getHeight());
		
		//poner las letras		
		Rectangle2D bounds = getFontMetrics(font).getStringBounds(message, g2);				
		if(color != null) {
			g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue()));
		}
		else g2.setColor(new Color(255,255,255));
		g2.setFont(font);
		g2.drawString(message, (int)((getWidth()/2)-(bounds.getWidth()/2)), (getHeight()/2)-(int)(bounds.getHeight()/2));
		bounds = getFontMetrics(font).getStringBounds(getText(), g2);
		g2.drawString(getText(), (int)((getWidth()/2)-(bounds.getWidth()/2)), (getHeight()/2)+(int)(bounds.getHeight()/2));
		
	}

	public void proccesAction(KeyEvent e) {
		
		if(e.getExtendedKeyCode() == e.VK_ENTER) {
			//si se le da enter hacer la accion definida
			actionPerformed();
		}
		/*
		 * el resto es para solo aceptar letras y espacios y numeros
		 * en el nombre puesto por el usuario
		 */
		else if(!Character.isAlphabetic(e.getKeyChar())) {
			if(Character.isDigit(e.getKeyChar())) {
				if(getText().length()>=30)return;
				setText(getText()+e.getKeyChar());
			}
			else if(e.VK_SPACE == e.getExtendedKeyCode())setText(getText()+e.getKeyChar());
			
			else if(e.getExtendedKeyCode() == e.VK_BACK_SPACE && !getText().isEmpty())				
				setText(getText().substring(0, getText().length()-1));
		}
		else {
			if(getText().length()>=30)return;
			setText(getText()+e.getKeyChar());
		}
			
	}
	
	
}
