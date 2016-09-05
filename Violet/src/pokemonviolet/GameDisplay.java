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
		 * <p> One frame dimensions: 20x20. </p>
		 */
		private static BufferedImage playerSprite;
		/**
		 * Current frame on Player Sprite.
		 */
		private static Image currentPlayerFrame;
		/**
		 * Current map.
		 */
		private BufferedImage mapRegion;
	// </editor-fold>
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
		try {
			playerSprite = ImageIO.read(new File("player.png"));
			mapRegion = MapBuilder.getMapRegion(1);
		} catch (IOException ex) {
		}
	}
	
	public void paint(Graphics g){
		/*
		int lengthOfMap = 29;
		int counter = 0;
		g.setColor(Color.ORANGE);
		for (int i = 0; i < lengthOfMap; i++) {
			for (int j = 0; j < lengthOfMap; j++) {
				if (counter%2 == 0){
					g.fillRect(0+(i*20), 0+(j*20), 20, 20);
				}
				counter = counter+1;
			}
		}
		*/
		g.drawImage(mapRegion, 0, 0, this);
		g.drawImage(currentPlayerFrame, Game.player.getX(),Game.player.getY(),(int)(Player.SPRITE_X*Player.SPRITE_RESIZE),(int)(Player.SPRITE_Y*Player.SPRITE_RESIZE),this);
		g.fillOval((int)(Game.player.getxTile()*Game.player.SPRITE_RESIZE*Game.player.SPRITE_X)+3, (int)(Game.player.getyTile()*Game.player.SPRITE_RESIZE*Game.player.SPRITE_Y)+3, 14, 14);
	}

    @Override
    public void run() {
        while (true){
			requestFocus();
			
			int xPlayerIMG = 0;
			int yPlayerIMG = 0;
			if (Game.player.getvDirection().compareTo("") != 0){
				Game.player.setCurFrame(Game.player.getCurFrame()+1);
				if (Game.player.isRunning()){
					xPlayerIMG = xPlayerIMG + 60;
				}
			}


			xPlayerIMG = xPlayerIMG + (Game.player.getCurFrame()*20);
			yPlayerIMG = yPlayerIMG + (Game.player.getCurAnim()*20);

			currentPlayerFrame = playerSprite.getSubimage(xPlayerIMG, yPlayerIMG, 20, 20);

			repaint((Game.player.getX()-20), (Game.player.getY()-20), 60, 60);
           
			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
