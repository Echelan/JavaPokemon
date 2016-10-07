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
public class Game extends Scene {

	public Game(Handler main) {
		super(main, "GAME", true);
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (action.compareTo("A") == 0) {
		} else if (action.compareTo("B") == 0) {
			main.player.setRunning(state.compareTo("PRESS") == 0);
		} else if (action.compareTo("START") == 0) {
			if (state.compareTo("RELEASE") == 0) {
				start();
			}
		} else if (state.compareTo("PRESS") == 0) {
			if (main.player.getvDirection().compareTo("") == 0) {
				main.player.setvDirection(action);
				main.player.setDirection(action);
			}
		} else if (state.compareTo("RELEASE") == 0) {
			if (main.player.getDirection().compareTo(action) == 0) {
				main.player.setDirection("");
			}
		}
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
	protected void move(String dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void start() {
		main.gameState.add(new Pause(main));
	}

	@Override
	public BufferedImage getDisplay() {
		int allW = pokemonviolet.model.Map.MAP_TOTAL_SIZE_X * 3, allH = pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y * 3;
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		for (int i = 0; i < main.displayedMaps.length; i++) {
			for (int j = 0; j < main.displayedMaps[i].length; j++) {
				if (main.displayedMaps[i][j] != null) {
					g.drawImage(main.displayedMaps[i][j].getImage(), main.curMapX + (int) (i * pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), main.curMapY + (int) (j * pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y), (int) (pokemonviolet.model.Map.MAP_TOTAL_SIZE_X), (int) (pokemonviolet.model.Map.MAP_TOTAL_SIZE_Y), null);
				}
			}
		}
		g.drawImage(main.player.getCurFrameImage(), ssX / 2, ssY / 2, (int) (main.player.SPRITE_X * main.player.SPRITE_RESIZE), (int) (main.player.SPRITE_Y * main.player.SPRITE_RESIZE), null);

		return display;
	}

}
