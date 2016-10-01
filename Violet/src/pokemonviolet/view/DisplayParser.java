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
	
	public static int curMapX, curMapY;
	private static final int ssX=pokemonviolet.model.Handler.SCREEN_SIZE_X, ssY=pokemonviolet.model.Handler.SCREEN_SIZE_Y;
	
	public static BufferedImage displayImage(){
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		int counter = 1;
		boolean done = false;
		int allW = pokemonviolet.model.Map.MAP_TOTAL_SIZE_X*3, allH = pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y*3;
		while (!done){
			Scene thisScene=pokemonviolet.model.Handler.gameState.get(Handler.gameState.size()-1);

			g.drawImage(Handler.gameState.get(Handler.gameState.size()-1).getDisplay(), 0,0, null);
			
			done=thisScene.isFull();
			
			counter = counter+1;
		}
		
		return display;
	}

}
