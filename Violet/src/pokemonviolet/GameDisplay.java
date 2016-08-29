/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Sprite for Player.
		 */
		BufferedImage playerSprite;
		/**
		 * Yellow Ball X Position.
		 */
		int x = 0;
		/**
		 * Yellow Ball Y Position.
		 */
		int y = 0;
		/**
		 * Yellow Ball X Direction Boolean.
		 */
		boolean xUp = false;
		/**
		 * Yellow Ball Y Direction Boolean.
		 */
		boolean yUp = false;
		
		Image currentPlayerFrame;
	// </editor-fold>
	
	public GameDisplay(){
		try {
			playerSprite = ImageIO.read(new File("player.png"));
		} catch (IOException ex) {
		//	Logger.getLogger(GameDisplay.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(currentPlayerFrame, Game.player.getX(), Game.player.getY(), this);
	}

    @Override
    public void run() {
        while (true){
			requestFocus();
			
			int xPlayerIMG = 0;
			int yPlayerIMG = 0;
			if (Game.player.getDirection().compareTo("")!=0){
				Game.player.setCurFrame(Game.player.getCurFrame()+1);
				if (Game.player.isRunning()){
					xPlayerIMG = xPlayerIMG + 60;
				}
			}
			xPlayerIMG = xPlayerIMG + (Game.player.getCurFrame()*20);
			yPlayerIMG = yPlayerIMG + (Game.player.getCurAnim()*20);
			
			currentPlayerFrame = playerSprite.getSubimage(xPlayerIMG, yPlayerIMG, 20, 20);
			//repaint(Game.player.getOldX(),Game.player.getOldY()-,50,50);
			repaint(Game.player.getX()-15,Game.player.getY()-15,50,50);
           
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
