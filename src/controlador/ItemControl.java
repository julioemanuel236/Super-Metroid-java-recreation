package controlador;

import modelo.*;
import modelo.items.Item;
import modelo.items.ItemCuracion;
import modelo.items.ItemMorphoSphere;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ItemControl {

	//imagenes de los items
	private static Image itemCuarcionSprite;
	private static Image itemMorphoSphereSprite;
	//lista de items
	public static List<Item> items = new List<>();
	
	private static void cargarItemCuracionSprites() {
		if(itemCuarcionSprite !=null)return;
		
		try {
			int scale = PlayerControl.SCALE;
			itemCuarcionSprite = ImageIO.read(ItemControl.class.getResource("/images/item/item_restore_life.png"));
			itemCuarcionSprite = itemCuarcionSprite .getScaledInstance(itemCuarcionSprite.getWidth(null)*scale , 
																	   itemCuarcionSprite.getHeight(null)*scale,
																	   Image.SCALE_SMOOTH);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void cargarItemMorphoSfereSprites() {
		if(itemMorphoSphereSprite!=null)return;
		
		try {
			int scale = PlayerControl.SCALE;
			itemMorphoSphereSprite = ImageIO.read(ItemControl.class.getResource("/images/item/item_morpho_sphere.png"));
			itemMorphoSphereSprite = itemMorphoSphereSprite.getScaledInstance(itemMorphoSphereSprite.getWidth(null)*scale , 
																			  itemMorphoSphereSprite.getHeight(null)*scale,
																	   Image.SCALE_SMOOTH);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ItemCuracion createItemCuracion() {
	
		cargarItemCuracionSprites();
		ItemCuracion ic = new ItemCuracion();
		ic.setIcon(new ImageIcon(itemCuarcionSprite));
		ic.setSize(itemCuarcionSprite.getWidth(null),itemCuarcionSprite.getHeight(null));
		return ic;
		
	}
	
	public static ItemMorphoSphere createItemMorphoSphere() {
		
		cargarItemMorphoSfereSprites();
		ItemMorphoSphere ims = new ItemMorphoSphere();
		ims.setIcon(new ImageIcon(itemMorphoSphereSprite));
		ims.setSize(itemMorphoSphereSprite.getWidth(null),itemMorphoSphereSprite.getHeight(null));
		return ims;
	}

	public static void update() {
		/*
		 * la manera de hacerlo es tomando cuantos items son
		 * esa cantidad de veces se coge el primero de la lista
		 * se quita de la lista, se procesa, y si no ha muerto
		 * se agrega al final para procesarlo en el proximo frame
		 */
		int n = items.size();
		while(n>0) {			
			Item e = items.poll();
			e.update();
			if(!e.isMuerto())items.add(e);
			n--;
		}
	}
}
