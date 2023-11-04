package controlador;

import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;
import modelo.*;
import modelo.mapa.Mapa;
import modelo.mapa.Pincho;
import modelo.mapa.Struct;
import modelo.mapa.StructCabina;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JComponent;

public class MapControl{
	
	//el mapa donde se jugara la partida actual
	public static Mapa mapa;
	
	//textura es la imagen de fondo
	private static Image textura;
	
	
	private static int width;
	private static int height;
	
	Container container;
	
	public static List<Struct> bloques =  new List<>();
	
	public MapControl(Container c) {		
		container = c;
	}
	
	public MapControl(int width ,int height,Container c){
		this.width = width;
		this.height = height;		
		container = c;		
	}

	public MapControl(Dimension size,Container c) {
		this.width = size.width;
		this.height = size.height;
		container = c;
	}
	
	public  static void cargarMapa(int nivel){
		//crear un mapa nuevo
		mapa = new Mapa(width,height);
		//cargar la textura del nivel
		
		cargarTextura(nivel);
		//escala por la cual multiplicar altura y anchura
		int scale = PlayerControl.SCALE;

		try {
			
			InputStream fr = MapControl.class.getResourceAsStream("/data/data_level_"+nivel+".txt");
			Scanner entry = new Scanner(fr);

			Mob m = null;
			//m sera la entidad que se creara cada linea
			int x;
			int y;
			int width;
			int height;
			int c = 1;
			while(entry.hasNextInt()) {
				//System.out.println(c);
				c++;
				//leer el tipo de entidad y su ubicacion
				int tipo = entry.nextInt();
				x = entry.nextInt();
				y = entry.nextInt();
				
				/*
				 * luego seleccionamos segun tipo
				 * y si hay que leer alto y ancho tambien se lee
				 * y se dan esas dimensiones a m
				 */
				if(tipo == 0) {
					m = new Struct();
					width = entry.nextInt();
					height = entry.nextInt();
					m.setSize(width*scale,height*scale);
				}
				
				else if(tipo == 1) {
					m = EnemyControl.createEnemyTipe1();
				}
				
				else if(tipo == 2) {
					m = new Pincho();
					width = entry.nextInt();
					height = entry.nextInt();
					m.setSize(width*scale,height*scale);
					m.setLastX(m.getX());
					m.setLastY(m.getY()); 
				}
				
				else if(tipo == 3) {
					m = ItemControl.createItemCuracion();
					width = entry.nextInt();
					height = entry.nextInt();
					m.setLocation(x,(y+height)-m.getHeight());
					m.setLastX(m.getX());
					m.setLastY(m.getY()); 
 				}

				else if(tipo == 4) {
					m = ItemControl.createItemMorphoSphere();
					width = entry.nextInt();
					height = entry.nextInt();
					m.setLocation(x,(y+height)-m.getHeight());
					m.setLastX(m.getX());
					m.setLastY(m.getY()); 
 				}
				else if(tipo == 5) {
					m = EnemyControl.createEnemyTipe2();
				}
				
				else if(tipo == 6) {
					m = new StructCabina();
					width = entry.nextInt();
					height = entry.nextInt();
					m.setSize(width*scale,height*scale);
				}
				//luego de procesar cada linea
				//si se creo una entidad se le da ubicacion
				if(m != null) {
					m.setLocation(x*scale,y*scale);
					m.setLastX(m.getX());
					m.setLastY(m.getY()); 
				}
				//y si agrega al mapa
				mapa.add(m);
			}
			
			entry.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarTextura(int nivel) {
		try {
			
			textura = ImageIO.read(MapControl.class.getResource("/images/nivel_"+nivel+".png"));
			textura = textura.getScaledInstance(textura.getWidth(null)*PlayerControl.SCALE, textura.getHeight(null)*PlayerControl.SCALE, Image.SCALE_FAST);					
			mapa.setFondo(textura);
			//System.out.println(mapa.getSize());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Mapa getMapa() {
		return mapa;
	}
	
	int stop = 100;
	
	public void reLocate(Point p) {
		/*
		 * reLocate es una funcion recursiva
		 * que mueve el mapa pixel a pixe mientras
		 * este a mayor a cierte distancia el personaje
		 * del centro de la pantalla
		 * se calculan la distancia
		 * se elije 1 o -1 segun esta a la derecha o la izquierda
		 * tambien se hace esto un maximo de 100 veces por cada frame
		 * para evitar un stack overflow en la llamada recursiva
		 *   
		 */
		
		int x = p.x + mapa.getX();
		int y = p.y + mapa.getY();
		
		int toX = container.getWidth()/2;
		int toY = container.getHeight()/2;
		
		int difX = Math.abs(x-toX);
		int difY = Math.abs(y-toY);
				
		if(difX<100 && difY<50)return;
				
		int dx = x>toX?-1:x==toX?0:1;
		int dy = y>toY?-1:y==toY?0:1;;
		
		if(difX<100)dx = 0;
		
		toX = mapa.getX()+dx;
		toY = mapa.getY()+dy;
				
		if(toX>0)toX=0;
		if(toX<-(mapa.getWidth()-container.getWidth()))toX=-(mapa.getWidth()-container.getWidth());		
		if(toY>0)toY=0;
		if(toY<-(mapa.getHeight()-container.getHeight()))toY=-(mapa.getHeight()-container.getHeight());
		
		mapa.setLocation(toX,toY);
		mapa.repaint();
		stop--;
		
		if(stop == 0) {
			return;
		}
		reLocate(p);
		stop = 100;
	}

	public void put(JComponent j) {
		mapa.add(j);
	}

	
}
