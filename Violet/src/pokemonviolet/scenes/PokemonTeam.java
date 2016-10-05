/*
 *  PokemonTeam Violet - A University Project by Andres Movilla
 *  PokemonTeam COPYRIGHT 2002-2016 PokemonTeam.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and PokemonTeam character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using PokemonTeam content on PokemonTeam Violet.
 */
package pokemonviolet.scenes;

import java.awt.image.BufferedImage;
import pokemonviolet.model.Handler;
import pokemonviolet.model.Pokemon;

/**
 *
 * @author Andres
 */
public class PokemonTeam extends Scene {
	
	private Pokemon[] team;
	
	public PokemonTeam(Handler main) {
		super(main, "TEAM", true);
		
		this.team = main.player.getTeam();
	}

	@Override
	public void receiveKeyAction(String action, String state) {
		if (state.compareTo("RELEASE") == 0) {
			if (action.compareTo("A") == 0) {
				accept();
			} else if (action.compareTo("B") == 0) {
				cancel();
			} else if (action.compareTo("START") == 0) {
			}
		} else if (state.compareTo("PRESS") == 0) {
			if (action.compareTo("UP") == 0 || action.compareTo("DOWN") == 0 || action.compareTo("LEFT") == 0 || action.compareTo("RIGHT") == 0) {
				move(action);
			}
		}
	}

	@Override
	protected void accept() {
		
	}

	@Override
	protected void cancel() {
		this.dispose();
	}

	@Override
	protected void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	@Override
	protected void move(String dir) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
