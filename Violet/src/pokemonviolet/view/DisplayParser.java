/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pokemonviolet.model.Handler;
import pokemonviolet.scenes.Scene;

/**
 *
 * @author Andres
 */
public abstract class DisplayParser {
	
	private static final int ssX=Handler.SCREEN_SIZE_X, ssY=Handler.SCREEN_SIZE_Y;
	
	public static BufferedImage displayImage(){
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		int counter = 0;
		boolean done = false;
		
		if (!Handler.gameState.isEmpty()){
			while (!done){
				counter = counter+1;
				Scene thisScene=Handler.gameState.get(Handler.gameState.size()-counter);

				done=thisScene.isFull();
			}

			for (int i = Handler.gameState.size()-counter; i < Handler.gameState.size(); i++) {
				g.drawImage(Handler.gameState.get(i).getDisplay(), 0,0, null);
			}
		}
		
		return display;
	}

}
