/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.image.BufferedImage;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public abstract class Scene {
	protected static final int ssX=pokemonviolet.model.Handler.SCREEN_SIZE_X, ssY=pokemonviolet.model.Handler.SCREEN_SIZE_Y;
	protected final Handler main;
	private final String name;
	private final boolean full;

	public Scene(Handler main, String name, boolean full) {
		this.main = main;
		this.name = name;
		this.full = full;
	}

	public abstract BufferedImage getDisplay();
	
	public abstract void receiveKeyAction(String action,String state);
	
	protected abstract void accept();
	
	protected abstract void cancel();
	
	protected abstract void dispose();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the full
	 */
	public boolean isFull() {
		return full;
	}
	
	
	
}
