package controlador;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;
import modelo.jugador.Record;
import vista.BarraVida;
import vista.MessageEntry;
import modelo.List;

public class GameControl {

	/*
	 * esto funciona como clase controladora general
	 * controla el flujo del juego
	 * resetea cada partida nueva
	 * guarda datos de partidas y puntaje
	 */
	
	//instancia de los controladores
	private PlayerControl player;
	private MapControl mapa;
	private ShotControl balas = new ShotControl();
	
	//referencia al cartel mensaje que puede haber en pantall
	public static vista.Message msg;	
	//puntos obtenidos en la partida actual	
	public static float puntos;
	
	//panel donde se pondra el mapa
	private JPanel panel;
	
	public GameControl(JPanel panel) {
		this.panel = panel;			
	}
	 
	//si el jugador no es null hacer alguna accion
	public void proccesAction(KeyEvent e) {
            if(player != null)
                player.proccesAction(e);				
	}
	
	//iniciar una nueva partida
	@SuppressWarnings("serial")
	public void startGame() {
		
		//primero se reinician todos los datos
		//se limpia el panel y se
        panel.removeAll();
        panel.repaint();
        puntos = 0;
        Global.frame = 0;	
        Global.pause = false;
        Global.win = false;	
        Global.gameOver = false;
                
        //crear un nuevo jugadorr
		player = new PlayerControl();
		//craer un mapa nuevo sobre este panel
		mapa = new MapControl(panel.getWidth(),panel.getHeight(),panel);
		
		//limpiar todas las listas de entidades
		//para por si quedo alguna entidad de la partida pasada no se procese
		EnemyControl.enemigos.clear();
		ItemControl.items.clear();
		ShotControl.balas.clear();
		UIControl.ui.clear();
		//cargar el nivel 1
		//actualmente el unico que tiene
		MapControl.cargarMapa(1);
		
		//crear la barra de vida
		//que nos informa de la energia actual del personaje
		BarraVida barra = new BarraVida(panel.getWidth()/3,panel.getHeight()/13);
		barra.setLocation(20,20);
		panel.add(barra);
				
		panel.add(mapa.getMapa());
		mapa.getMapa().add(PlayerControl.getPlayer());							
		
		PlayerControl.getPlayer().setLocation(200,250);										
		
		//la variable time sevira para medir el tiempo que se quedan
		//los mensajes de game over y victoria en pantalla		
		long time = 0;
		
		//mientras no haya terminado el juego
		while (!Global.gameOver) {
                    
            if(Global.win) {
            	//si se gano y el time es 0 darle el tiempo actual
				if(time == 0)time = System.currentTimeMillis(); 
				//si ya paso el tiempo de espera
				//2 segundo en este caso dar Game Over
				if(System.currentTimeMillis()-time>2000)Global.gameOver = true;
				//si msg es null crear un nuevo mensaje de victoria
				if(msg == null) {
					msg = new vista.Message("CONGRATULATIONS");
					msg.setColor(java.awt.Color.green);
					msg.addTo(mapa.container);
					msg.repaint();
				}

			}
			
			else if(Global.pause) {
				//en caso de que se este en pause
				//si msg es null crear un nuevo mensaje de pausa
				if(msg == null) {
					msg = new vista.Message("PAUSE");					
					msg.addTo(mapa.container);
					msg.repaint();
				}				
				
			}
			else {
				//si no se ha ganado y no esta en pausa
				
				if(PlayerControl.getPlayer().isMuerto()) {
					//si el jugador esta muerto
					//actualizar tiempo de muerte para tener el cartel de game over en pantalla
					if(time == 0)time = System.currentTimeMillis(); 
					if(System.currentTimeMillis()-time>2000)Global.gameOver = true;
					//si msg es null crear un cartel de gamer over
					if(msg == null) {
						msg = new vista.Message("GAME OVER");
						msg.setColor(java.awt.Color.red);
						msg.addTo(mapa.container);
						msg.repaint();
					}
					
				}
				
				//si el jugador no ha muerto actualizar el jugador
				if(!PlayerControl.getPlayer().isMuerto())
					player.update();
				
				//actualizar la barra de vida
				barra.setVidaMaxima(PlayerControl.getPlayer().getVidaMaxima());
				barra.setVidaObjetivo(PlayerControl.getPlayer().getVida());
				//actualizar las balas
				balas.update();
				//actualizar la camara
				mapa.reLocate(PlayerControl.getPlayer().getLocation());
								
				
				//actualizar los enemigos, items y elementos de la UI
				EnemyControl.update();
				
				ItemControl.update();
				
				UIControl.update();
				
				//repintar todo para ver los cambios
				mapa.getMapa().repaint();

				//aumentar el frame
				Global.frame++;															
								
			}
			
			try {
				//hacer una puasa de 15 mili segundos
				Thread.sleep(15);
			} 
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}				
		// si al terminar quedo algun mensaje en pantalla quitarlo
		if(msg != null)msg.quit();
		
		//crear un mensaje nuevo den tipo entry para leer el nombre del jugador
		
		msg = new MessageEntry("Tu Nombre: ") {
			
			@Override
			public void quit() {
				
				//cuando este mensaje se quite se ira al menu 
				super.quit();
				menu();
                msg = null;
			}
			
			@Override
			public void actionPerformed() {
				
				/*
				 * la accion sera actualizar la cantidad de partidas
				 * la cantidad ganada y el porcentaj
				 * 
				 * luego cargar todas las puntuaciones anteriores
				 * y a medida de van cargando se van agregando auna lista
				 * pero antes se comprueba si la puntuacion de la partida recien jugada
				 * tiene mayor puntaje que la que estas cargando, si es asi se inserta primero
				 * y se cargan las demas
				 * 
				 */
                int partidasTotales = 0;
                int partidasGanadas = 0;
                try{
                	                	
                    InputStream fr = new FileInputStream("Info Partidas.txt");
                    Scanner entry = new Scanner(fr);
                    //cargar partidas totales
                    partidasTotales = entry.nextInt();
                    //omitira palabra                     
                    entry.next();
                    //partidas ganadas
                    partidasGanadas = entry.nextInt();
                    //cerrar el fichero
                    entry.close();
                    fr.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                	//aumentar las partidas totatles
                    partidasTotales++;
                    //si se gano aumentar las ganadas
                    if(Global.win)partidasGanadas++;
                try{
                	/*
                	 * guardar la nueva informacion
                	 */
                    FileWriter fr = new FileWriter("Info Partidas.txt");
                    
                    fr.write(partidasTotales+" JUGADAS\n");
                    fr.write(partidasGanadas+" GANADAS\n");
                    fr.write(((float)partidasGanadas/(float)partidasTotales)*100f+"%\n");
                    
                    fr.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
                
                //lista de records 
                List<Record> record = new List<>();
				
				try {
					//el nuevo record que se hizo
					Record now = new Record(this.getText(),(int)GameControl.puntos);										
					
					InputStream fr = new FileInputStream("score.txt");
					Scanner entry = new Scanner(fr);
					boolean insertado = false;
					while(entry.hasNext()) {
						String nombre = entry.nextLine();
						int puntaje = Integer.parseInt(entry.nextLine());
						//cargar de uno en uno
						Record r = new Record(nombre,puntaje);
						//si no se ha puesto el nuevo y es mayor que el que se cargo
						if(!insertado && now.compareTo(r) == -1) {
							record.add(now);//se agrega y se marca ya insertado
							insertado = true;
						}					
						record.add(r);
					}
					
					entry.close();
					fr.close();
					
					if(!insertado)record.add(now);
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				try {
					FileWriter fw = new FileWriter("score.txt");
					//guarar las nuevas puntuaciones
					for(int i=0 ;i<record.size(); i++) {
						Record r = record.get(i);
						fw.write(r.getNombre()+"\n");
						fw.write(r.getPuntaje()+"\n");
					}
					fw.close();
					quit();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		msg.addTo(mapa.container);

	}

	public void menu() {
		//System.out.println("DESPLIEGUE DE MENU PRINCIPAL");
		panel.removeAll();
		panel.add(new vista.MenuPanel(this));
                panel.repaint();
	}

    public JPanel getPanel() {
        return panel;
    }
	        
}
