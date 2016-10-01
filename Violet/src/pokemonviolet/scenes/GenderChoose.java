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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class GenderChoose extends Scene{
	
	private int chosen=1;

	public GenderChoose(Handler main) {
		super(main, "GENDER", true);
	}
	
	@Override
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage( ssX, ssY, BufferedImage.TYPE_INT_RGB);
		Graphics g = display.getGraphics();
		
		try {
			g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0,0,null);
		} catch (IOException ex) {
		}
		
		return display;
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
		main.startGame();
		main.gameState.remove(main.gameState.size()-1);
		main.gameState.add(new Game(main));
	}
	
}
