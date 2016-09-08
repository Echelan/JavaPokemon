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

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
		
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay(){
	}
	
	public void paint(Graphics g){
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawImage(Game.displayedMaps[i][j].getImage(), Game.displayedMaps[i][j].getX(), Game.displayedMaps[i][j].getY(), this);
			}
		}
		
		g.drawImage(Game.player.getCurFrameImage(), this.getWidth()/2, this.getHeight()/2,(int)(Game.player.SPRITE_X*Game.player.SPRITE_RESIZE),(int)(Game.player.SPRITE_Y*Game.player.SPRITE_RESIZE), this);
	}
	
    @Override
    public void run() {
        while (true){
			requestFocus();

			repaint();
			
			try {
				Thread.sleep(60);
			} catch (InterruptedException ex) {
			}
        }
    }
	
}
