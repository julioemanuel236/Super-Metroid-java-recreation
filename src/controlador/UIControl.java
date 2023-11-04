package controlador;

import java.awt.Component;
import modelo.List;

public class UIControl {

	
	public static List<Component> ui = new List<>();
	
	
	public static void update() {

		for(int i=0 ; i<ui.size() ;i++) {
			Component c = ui.poll();	
			c.repaint();
			if(c.getParent() != null)ui.add(c);
		}
		
	}
	
}
