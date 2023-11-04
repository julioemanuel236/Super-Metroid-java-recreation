package controlador;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import modelo.*;
import modelo.jugador.Samus;

import javax.swing.ImageIcon;

public class PlayerControl {	
	
	private boolean LEFT = false, RIGHT = false,JUMP = false, SHOTING, SIT, BALL;
	private boolean isSit = false;	
	private int xMove = 10;
	private int moving = 0;
	public static final int SCALE = 2;
	private List<Image> sprite_left = new List<>();
	private List<Image> sprite_right = new List<>();
	private List<Image> sprite_right_shot = new List<>();
	private List<Image> sprite_left_shot = new List<>();
	private List<Image> sprite_left_ball = new List<>();
	private List<Image> sprite_right_ball = new List<>();
	private List<Image> sprite_right_jump = new List<>();
	private List<Image> sprite_left_jump= new List<>();
	private Image stand_by;
	private Image stand_by_left_shot;
	private Image stand_by_right_shot;
	private Image sit_right;
	private Image sit_left;	
	private  static Samus player;

	public PlayerControl() {
		cargarSprite();
                player = new Samus();
		player.setSize(24, 39);		
		player.setIcon(new ImageIcon(stand_by));
		player.setFocusable(true);
		player.requestFocusInWindow();		
	}

	public void cargarSprite() {
		/*
		 * esta funcion carga los sprites que se usan
		 * para el movimiento del jugador
		 */
		try {
			int frame = 6;
			int scale = SCALE;
			// moviniento normal izquierda
			Image img = ImageIO.read(getClass().getResource("/images/player/player_left_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_left_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_left_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left.add(img);

			// moviniento normal izquierda disparo
			img = ImageIO.read(getClass().getResource("/images/player/player_left_shot_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_shot.add(img);
			
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_shot_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_shot.add(img);
			

			img = ImageIO.read(getClass().getResource("/images/player/player_left_shot_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_shot.add(img);
			

			//movimiento bolita izquierda
			img = ImageIO.read(getClass().getResource("/images/player/player_left_ball_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_ball.add(img);
			
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_ball_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_ball.add(img);
			
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_ball_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_ball.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_ball_4.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_ball.add(img);
			
			//movimiento salto izquierda
			img = ImageIO.read(getClass().getResource("/images/player/player_left_jump_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_jump.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_jump_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_jump.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_jump_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_jump.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_left_jump_4.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_left_jump.add(img);
			
			// moviniento normal derecha
			img = ImageIO.read(getClass().getResource("/images/player/player_right_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_right_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_right_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right.add(img);

			// moviniento normal derecha disparo
			img = ImageIO.read(getClass().getResource("/images/player/player_right_shot_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_shot.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_shot_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_shot.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_right_shot_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_shot.add(img);

			//movimiento bolita derecha
			img = ImageIO.read(getClass().getResource("/images/player/player_right_ball_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_ball.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_ball_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_ball.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_ball_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_ball.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_ball_4.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);			
			for(int i=0;i<frame;i++)	
				sprite_right_ball.add(img);

			//movimiento salto derecha
			img = ImageIO.read(getClass().getResource("/images/player/player_right_jump_1.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_jump.add(img);

			img = ImageIO.read(getClass().getResource("/images/player/player_right_jump_2.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_jump.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_jump_3.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_jump.add(img);
			
			img = ImageIO.read(getClass().getResource("/images/player/player_right_jump_4.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			for(int i=0;i<frame;i++)
				sprite_right_jump.add(img);
			
			// normal en espera
			img = ImageIO.read(getClass().getResource("/images/player/player_stand_by.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			stand_by = img;

			// en espera disparo derecha
			img = ImageIO.read(getClass().getResource("/images/player/player_stand_by_right_shot.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			stand_by_right_shot = img;

			// en espera disparo izquierda
			img = ImageIO.read(getClass().getResource("/images/player/player_stand_by_left_shot.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			stand_by_left_shot = img;

			// agachado disparo izquierda
			img = ImageIO.read(getClass().getResource("/images/player/player_sit_left.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			sit_left = img;

			// agachado disparo derecha
			img = ImageIO.read(getClass().getResource("/images/player/player_sit_right.png"));
			img = img.getScaledInstance(img.getWidth(null) * scale, img.getHeight(null) * scale, Image.SCALE_SMOOTH);
			sit_right = img;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	public static Samus getPlayer() {
		return player;
	}
	
	public void proccesAction(KeyEvent e) {
		/*
		 * marcar que se presiono una tecla para en el update procesar segun
		 * se deba
		 */
		if (e.getID() == e.KEY_PRESSED) {
			if (e.getExtendedKeyCode() == e.VK_D || e.getExtendedKeyCode() == e.VK_RIGHT)
				{RIGHT = true; player.setdMove(xMove);player.setFacingRight();}
			else if (e.getExtendedKeyCode() == e.VK_A || e.getExtendedKeyCode() == e.VK_LEFT)
				{LEFT = true; player.setaMove(xMove);player.setFacingLeft();}
			else if (e.getExtendedKeyCode() == e.VK_SPACE)
				{SHOTING = true;}
			else if (e.getExtendedKeyCode() == e.VK_S || e.getExtendedKeyCode() == e.VK_DOWN)
				{SIT = true;/*player.setsMove(10);*/}
			else if (e.getExtendedKeyCode() == e.VK_W || e.getExtendedKeyCode() == e.VK_UP)
				{JUMP = true;}
			else if (e.getExtendedKeyCode() == e.VK_SHIFT || e.getExtendedKeyCode() == e.VK_X)
				{BALL = true;}
			
		}
		/*
		 * desmarcar las teclas que se suelten
		 */
		if (e.getID() == e.KEY_RELEASED) {
			if (e.getExtendedKeyCode() == e.VK_D || e.getExtendedKeyCode() == e.VK_RIGHT)
				{RIGHT = false;player.setdMove(0);}
			else if (e.getExtendedKeyCode() == e.VK_A || e.getExtendedKeyCode() == e.VK_LEFT)
				{LEFT = false;player.setaMove(0);}
			else if (e.getExtendedKeyCode() == e.VK_SPACE)
				SHOTING = false;
			else if (e.getExtendedKeyCode() == e.VK_S || e.getExtendedKeyCode() == e.VK_DOWN)
				{SIT = false;/*player.setsMove(0);*/}
			else if (e.getExtendedKeyCode() == e.VK_W || e.getExtendedKeyCode() == e.VK_UP)
				{JUMP = false;}
			else if (e.getExtendedKeyCode() == e.VK_SHIFT || e.getExtendedKeyCode() == e.VK_X)
				{BALL = false;}
		}
	}

	public Image agacharse() {
		Image img;
		if(player.isFacingRight())img = sit_right;
		else img = sit_left;		
		isSit = true;
		player.setSit(true);
		return img;
	}
	
		
	public Image pararse() {
		Image img;
		if(SHOTING) {
			if(player.isFacingRight())img = stand_by_right_shot;
			else img = stand_by_left_shot;
		}		
		else img = stand_by;		
		isSit=false;
		player.setSit(false);
		return img;
	}
	
	
	public void update() {
		//System.out.println(player.isInAir());
						
		if(JUMP && !player.isInAir()) {
			//si se esta presionando para saltar
			//y no se esta en el aire
			//dar potencia de salto
			//establecer en el aire			
			player.setJumpForce(35);
			player.setJumping(true);
			player.setInAir(true);
		}
		if(!player.isInAir()) {
			//en caso de no estar en el aire
			player.setJumpForce(0);
			player.setJumping(false);
		}
		
		//establecer si se esta moviendo para la izquierda
		//para la derecha o no se esta moviendo horizontalmente
		if(SIT || isSit)moving = 0;
		else if (LEFT && RIGHT) moving = 0;
		else if (LEFT) {
			moving = -1;
			player.setFacingLeft();
		}
		else if (RIGHT) {
			moving = 1;
			player.setFacingRight();
		}
		else moving = 0;
		
		
		Image img = stand_by;
		switch (moving) {
			//segun no se esta moviendo
			//se mueve a la izquierda o a la derecha
			case 0: {		

				if(player.isJumping()) {
					if(player.isFacingRight())img = sprite_right_jump.getNext();
					else img = sprite_left_jump.getNext();						
				}
				else if(SIT && !isSit)img = agacharse();							
				else if(!SIT && isSit)img = pararse();
				else if(SIT && isSit) {
					if(player.isFacingRight())img = sit_right;
					else img = sit_left;
				}
				else {
					if(SHOTING) {
						if(player.isFacingRight())img = stand_by_right_shot;
						else img = stand_by_left_shot;
						
					}
					else if(BALL && player.isCanMakeBall()) {
						if(player.isFacingRight())img = sprite_right_ball.getCurrent();
						else img = sprite_left_ball.getCurrent();
					}
				}
				
			}break;
			
			case 1: {
				// MOVING right
				//System.out.println(isSit);
				int fps = 4;
				img = sprite_right.getNext();
				if(player.isJumping() && player.isInAir())img = sprite_right_jump.getNext();
				else if (SHOTING) img = sprite_right_shot.get(sprite_right.getCurrentPosition());								
				else if(BALL && player.isCanMakeBall())img = sprite_right_ball.getNext();
				//update para no tener retardo entre el movimiento y la animacion

			}break;
			
			case -1: {
				// MOVING LEFT
				//System.out.println(isSit);
				int fps = 4;
				img = sprite_left.getNext();
				if(player.isJumping() && player.isInAir())img = sprite_left_jump.getNext();
				else if (SHOTING) img = sprite_left_shot.get(sprite_left.getCurrentPosition());				
				else if(BALL && player.isCanMakeBall())img = sprite_left_ball.getNext();				
				//update para no tener retardo entre el movimiento y la animacion									
				
			}break;					
		}
		
		if(player.getJumpForce()>0)player.setJumpForce(player.getJumpForce()-1);
		if(player.getJumpForce()<0)player.setJumpForce(0);
		//System.out.println(JUMP+" "+player.isInAir() + " "+jumpForce);
		int dx = player.getxMove();
		
		if(player.isGodMode()) {
			img = stand_by;
			player.setInAir(false);
			player.setInmortal(true);
			player.setGravity(false);
			if(JUMP)player.setwMove(10);
			else player.setwMove(0);
			if(LEFT)player.setaMove(xMove);
			else player.setaMove(0);
			if(RIGHT)player.setdMove(xMove);
			else player.setdMove(0);
			if(SIT)player.setsMove(10);			
			else player.setsMove(0);
			player.setLocation(player.getX() + dx,player.getY()+(player.getyMove()));
		}
		
		else {
			if(moving==0)dx = 0;			
			player.moveTo(player.getX() + dx,player.getY()+(player.getyMove()));
		}
		//System.out.println(player.getJumpForce());
		//actualizar el frame		
		((Samus)player).getDefaultLocale();
		player.update(img);
		if(SHOTING && player.canShot())
			//si se esta disparando y puede dispara , disparar
			player.shot();

				
	}
	
}
