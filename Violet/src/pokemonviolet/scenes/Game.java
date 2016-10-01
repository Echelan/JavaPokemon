/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class Game extends Scene{

	public Game(Handler main) {
		super(main, "GAME",true);
	}

	@Override
	public BufferedImage getDisplay() {
		int allW = pokemonviolet.model.Map.MAP_TOTAL_SIZE_X*3, allH = pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y*3;
		BufferedImage tempStitched = new BufferedImage( allW, allH, BufferedImage.TYPE_INT_RGB);
		Graphics g = tempStitched.getGraphics();
		
		for (int i = 0; i < pokemonviolet.model.Handler.displayedMaps.length; i++) {
			for (int j = 0; j < pokemonviolet.model.Handler.displayedMaps[i].length; j++) {
				g.drawImage(pokemonviolet.model.Handler.displayedMaps[i][j].getImage(), (int)(i*pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), (int)(j*pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y),(int)(pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), (int)(pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y), null);
			}
		}
		g.drawImage(pokemonviolet.model.Handler.player.getCurFrameImage(), ssX/2, ssY/2,(int)(pokemonviolet.model.Handler.player.SPRITE_X*pokemonviolet.model.Handler.player.SPRITE_RESIZE),(int)(pokemonviolet.model.Handler.player.SPRITE_Y*pokemonviolet.model.Handler.player.SPRITE_RESIZE), null);
		
		return tempStitched;
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void accept() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void cancel() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void dispose() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
