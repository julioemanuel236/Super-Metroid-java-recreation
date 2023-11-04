package vista;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import controlador.*;
import java.awt.Dimension;

public class Ventana extends JFrame{

	/*
	 * 
	 * ventana principal y main del programa
	 * crea un nuevo JForm
	 * crea un GameControl
	 * y llama menu para mostrar el menu
	 * aparte es el que delega todas
	 * las pulsaciones de teclado
	 * 
	 */
	public Ventana() {
		setSize(1024,720);
                setPreferredSize(new Dimension(1024,720));
		setResizable(false);		
		setLayout(null);
		setLocationRelativeTo(null);
                setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Ventana test = new Ventana();
		GameControl gm = new GameControl((JPanel)test.getContentPane());		
		test.addKeyListener(new KeyListener(){
			
			@Override
			public void keyTyped(KeyEvent e) {
								
			}

			@Override
			public void keyPressed(KeyEvent e) {				
				if(GameControl.msg != null && GameControl.msg instanceof MessageEntry) {
					((MessageEntry)GameControl.msg).proccesAction(e);
				}
				if (e.getExtendedKeyCode() == e.VK_ESCAPE) {
					if(GameControl.msg != null && Global.canQuitMessageScreen()) {						
						GameControl.msg.quit();
						GameControl.msg = null;
					}
					Global.pause = !Global.pause;
					
				}					
				gm.proccesAction(e);								
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				gm.proccesAction(e);
				
			}
			
		});
		test.repaint();
		gm.menu();
	}
				
	
	
}
	

