package controlador;

import modelo.*;
import modelo.enemigo.Enemigo;
import modelo.enemigo.EnemyType1;
import modelo.enemigo.EnemyType2;
import java.awt.Image;
import javax.imageio.ImageIO;

public class EnemyControl {
	//una lista que contiene los enemigos que estan en el mapa
	public static List<Enemigo> enemigos =  new List<>();
	
	//los sprites de animacion de cada enemigo
	private static List<Image> sprites_enemy_1;
	private static List<Image> sprites_enemy_2;
	private static Image sprite_enemy_2_fall;
	
	//actualizar cada enemigo
	public static void update() {
		/*
		 * la manera de hacerlo es tomando cuantos enemigos son
		 * esa cantidad de veces se coge el primero de la lista
		 * se quita de la lista, se procesa, y si no ha muerto
		 * se agrega al final para procesarlo en el proximo frame
		 */
		int n = enemigos.size();
	
		while(n>0) {			
			Enemigo e = enemigos.poll();
			e.update();
			if(!e.isMuerto())enemigos.add(e);
			else GameControl.puntos += e.getValor();
			n--;
		}
		
	}
	
	private static void cargarTexturasEnemyType1() {
		if(sprites_enemy_1 != null)return;
		sprites_enemy_1 = new List<>();
		
		try {
			int frames = 60;
			Image img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_1_1.png"));
			img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
			for(int i=0;i<frames;i++)
				sprites_enemy_1.add(img);
			
			
			img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_1_2.png"));
			img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
			for(int i=0;i<frames;i++)
				sprites_enemy_1.add(img);
			
			img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_1_3.png"));
			img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
			for(int i=0;i<frames;i++)
				sprites_enemy_1.add(img);
			
			img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_1_2.png"));
			img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
			for(int i=0;i<frames;i++)
				sprites_enemy_1.add(img);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void cargarTexturasEnemyType2() {		
		if(sprites_enemy_2 == null) 
			try {
				int frames = 60;
				sprites_enemy_2 = new List<>();
				
				Image img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_2_1.png"));
				img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
				for(int i=0;i<frames;i++)
					sprites_enemy_2.add(img);
				
				
				img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_2_2.png"));
				img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
				for(int i=0;i<frames;i++)
					sprites_enemy_2.add(img);
				
				img = ImageIO.read(EnemyControl.class.getResource("/images/enemy/enemy_type_2_3.png"));
				img = img.getScaledInstance(PlayerControl.SCALE*img.getWidth(null), PlayerControl.SCALE*img.getHeight(null), Image.SCALE_SMOOTH);
				sprite_enemy_2_fall = img;
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public static EnemyType1 createEnemyTipe1() {
		cargarTexturasEnemyType1();
		EnemyType1 enemy = new EnemyType1(sprites_enemy_1);		
		return enemy;		
	}
	
	public static EnemyType2 createEnemyTipe2() {
		cargarTexturasEnemyType2();
		EnemyType2 enemy = new EnemyType2(sprites_enemy_2,sprite_enemy_2_fall);		
		return enemy;
	}
}
